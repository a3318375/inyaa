package com.inyaa.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.dao.SysApiDao;
import com.inyaa.web.auth.service.OauthUserService;
import com.inyaa.web.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.access.event.LoggerListener;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.CollectionUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>WebSecurityConfig</h1>
 * Created by hanqf on 2020/11/11 17:01.
 */

@EnableWebSecurity
@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final ResourceAccessDeniedHandler resourceAccessDeniedHandler;
    private final SysApiDao sysApiDao;
    private final OauthUserService oauthUserService;
    private final ApiAccessDecisionManager apiAccessDecisionManager;
    private final InyaaFilterInvocationSecurityMetadataSource inyaaFilterInvocationSecurityMetadataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //开启跨域
        http.cors();
        List<String> list = sysApiDao.findUrlByAllowAccess();
        list.add("/login");
        list.add("/swagger-ui/**");
        list.add("/server/v3/api-docs**");
        String[] urls = list.toArray(new String[0]);
        //权限控制
        http.authorizeRequests()//登录成功就可以访问
                //不需要登录就可以访问
                .mvcMatchers(urls).permitAll()
                ///所有路径都需要登录
                .anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(inyaaFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(apiAccessDecisionManager);
                return o;
            }
        });


        http.oauth2Login().defaultSuccessUrl("https://oauth.inyaa.cn")
                .userInfoEndpoint().userService(oauthUserService);
        http.oauth2Client();
        http.logout().logoutSuccessHandler((req, resp, authentication) -> {
            BaseResult<String> res = BaseResult.success();
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(res));
            out.flush();
            out.close();
        });
        http.csrf().disable();
        http.exceptionHandling()
                //access_token无效或过期时的处理方式
                .authenticationEntryPoint(authenticationEntryPoint)
                //access_token认证后没有对应的权限时的处理方式
                .accessDeniedHandler(resourceAccessDeniedHandler);
    }

    @Bean
    RestTemplateCustomizer restTemplateCustomizer(OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        return restTemplate -> { //1 RestTemplateCustomizer时函数接口，入参是RestTemplate
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            if (CollectionUtils.isEmpty(interceptors)) {
                interceptors = new ArrayList<>();
            }
            interceptors.add((request, body, execution) -> { //2 通过增加RestTemplate拦截器，让每次请求添加Bearer Token（Access Token）；ClientHttpRequestInterceptor是函数接口，可用Lambda表达式来实现
                try {
                    OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken)
                            SecurityContextHolder.getContext().getAuthentication();
                    String clientRegistrationId = auth.getAuthorizedClientRegistrationId();
                    String principalName = auth.getName();
                    OAuth2AuthorizedClient client =
                            oAuth2AuthorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName); //3 OAuth2AuthorizedClientService可获得用户的OAuth2AuthorizedClient
                    if (client == null) {
                        //如果客户端信息使用的是基于内存的InMemoryOAuth2AuthorizedClientService，则重启服务器就会失效，需要重新登录才能恢复，
                        // 建议使用基于数据库的JdbcOAuth2AuthorizedClientService，本例使用的就是JdbcOAuth2AuthorizedClientService
                        throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "用户状态异常，请重新登录");
                    }
                    String accessToken = client.getAccessToken().getTokenValue(); //4 OAuth2AuthorizedClient可获得用户Access Token
                    request.getHeaders().add("Authorization", "Bearer " + accessToken); //5 将Access Token通过头部的Bearer Token中访问Resource Server
                } catch (Exception e) {
                    log.error("添加请求头异常", e);
                }

                log.info(String.format("请求地址: %s", request.getURI()));
                log.info(String.format("请求头信息: %s", request.getHeaders()));

                ClientHttpResponse response = execution.execute(request, body);
                log.info(String.format("响应头信息: %s", response.getHeaders()));

                return response;
            });
            restTemplate.setInterceptors(interceptors);
        };
    }

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcOperations jdbcOperations, ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository);
    }

    /**
     * 认证事件监听器，打印日志
     * <p>
     * 如：认证失败/成功、注销，等等
     */
    @Bean
    public LoggerListener loggerListener() {
        return new LoggerListener();
    }

    /**
     * 资源访问事件监听器，打印日志
     * <p>
     * 如：没有访问权限
     */
    @Bean
    public LoggerListener eventLoggerListener() {
        return new LoggerListener();
    }

}
