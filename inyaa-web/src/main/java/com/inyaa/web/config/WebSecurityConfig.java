package com.inyaa.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.dao.SysApiDao;
import com.inyaa.web.auth.service.OauthUserService;
import com.inyaa.web.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
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
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


        http.oauth2Login(oauth2LoginConfigurer -> oauth2LoginConfigurer
                .tokenEndpoint(tokenEndpointConfig -> tokenEndpointConfig.accessTokenResponseClient(oAuth2AccessTokenResponseClient()))
                .defaultSuccessUrl("https://www.inyaa.cn/oauth")
                .userInfoEndpoint().userService(oauthUserService)
        );

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
     * qq获取access_token返回的结果是类似get请求参数的字符串，无法通过指定Accept请求头来使qq返回特定的响应类型，并且qq返回的access_token
     * 也缺少了必须的token_type字段（不符合oauth2标准的授权码认证流程），spring-security默认远程获取
     * access_token的客户端是{@link DefaultAuthorizationCodeTokenResponseClient}，所以我们需要
     * 自定义{@link QqoAuth2AccessTokenResponseHttpMessageConverter}注入到这个client中来解析qq的access_token响应信息
     *
     * @return {@link DefaultAuthorizationCodeTokenResponseClient} 用来获取access_token的客户端
     * @see <a href="https://www.oauth.com/oauth2-servers/access-tokens/authorization-code-request">authorization-code-request规范</a>
     * @see <a href="https://www.oauth.com/oauth2-servers/access-tokens/access-token-response">access-token-response规范</a>
     * @see <a href="https://wiki.connect.qq.com/%E5%BC%80%E5%8F%91%E6%94%BB%E7%95%A5_server-side">qq开发文档</a>
     */
    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> oAuth2AccessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(),

                // 解析标准的AccessToken响应信息转换器
                new OAuth2AccessTokenResponseHttpMessageConverter(),

                // 解析qq的AccessToken响应信息转换器
                new QqoAuth2AccessTokenResponseHttpMessageConverter(MediaType.TEXT_HTML)));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        client.setRestOperations(restTemplate);
        return client;
    }

    /**
     * 自定义消息转换器来解析qq的access_token响应信息
     *
     * @see OAuth2AccessTokenResponseHttpMessageConverter#readInternal(java.lang.Class, org.springframework.http.HttpInputMessage)
     * @see OAuth2LoginAuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    private static class QqoAuth2AccessTokenResponseHttpMessageConverter extends OAuth2AccessTokenResponseHttpMessageConverter {

        public QqoAuth2AccessTokenResponseHttpMessageConverter(MediaType... mediaType) {
            setSupportedMediaTypes(Arrays.asList(mediaType));
        }

        @SneakyThrows
        @Override
        protected OAuth2AccessTokenResponse readInternal(Class<? extends OAuth2AccessTokenResponse> clazz, HttpInputMessage inputMessage) {

            String response = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);

            log.info("qq的AccessToken响应信息：{}", response);

            // 解析响应信息类似access_token=YOUR_ACCESS_TOKEN&expires_in=3600这样的字符串
            Map<String, String> tokenResponseParameters = Arrays.stream(response.split("&")).collect(Collectors.toMap(s -> s.split("=")[0], s -> s.split("=")[1]));

            // 手动给qq的access_token响应信息添加token_type字段，spring-security会按照oauth2规范校验返回参数
            tokenResponseParameters.put(OAuth2ParameterNames.TOKEN_TYPE, "bearer");
            return this.tokenResponseConverter.convert(tokenResponseParameters);
        }

        @Override
        protected void writeInternal(OAuth2AccessTokenResponse tokenResponse, HttpOutputMessage outputMessage) {
            throw new UnsupportedOperationException();
        }
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
