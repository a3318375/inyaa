package com.inyaa.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.access.event.LoggerListener;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <h1>WebSecurityConfig</h1>
 * Created by hanqf on 2020/11/11 17:01.
 */

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private ResourceAccessDeniedHandler resourceAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //开启跨域
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //权限控制
        http.authorizeRequests()//登录成功就可以访问
                .antMatchers("/res/**", "/user/**").authenticated()
                //需要具备相应的角色才能访问
                //.antMatchers("/user/**").hasAnyRole("admin", "user")
                //不需要登录就可以访问
                .antMatchers("/login","/swagger-ui/**", "/server/v3/api-docs**").permitAll()
                //其它路径需要根据指定的方法判断是否有权限访问，基于权限管理模型认证
                .anyRequest().access("@rbacService.hasPerssion(request,authentication)");


        //鉴权时只支持Bearer Token的形式，不支持url后加参数access_token
        http.oauth2ResourceServer()//开启oauth2资源认证
                .jwt() //token为jwt
                //默认情况下，权限是scope，而我们希望使用的是用户的角色，所以这里需要通过转换器进行处理
                .jwtAuthenticationConverter(jwt -> { //通过自定义Converter来指定权限，Converter是函数接口，当前上下问参数为JWT对象
                    Collection<SimpleGrantedAuthority> authorities =
                            ((Collection<String>) jwt.getClaims()
                                    .get("authorities")).stream() //获取JWT中的authorities
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toSet());

                    //如果希望保留scope的权限，可以取出scope数据然后合并到一起，这样因为不是以ROLE_开头，所以需要使用hasAuthority('SCOPE_any')的形式
                    Collection<SimpleGrantedAuthority> scopes = ((Collection<String>) jwt.getClaims()
                            .get("scope")).stream().map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                            .collect(Collectors.toSet());
                    //合并权限
                    authorities.addAll(scopes);
                    return new JwtAuthenticationToken(jwt, authorities);
                });

        http.exceptionHandling()
                //access_token无效或过期时的处理方式
                .authenticationEntryPoint(authenticationEntryPoint)
                //access_token认证后没有对应的权限时的处理方式
                .accessDeniedHandler(resourceAccessDeniedHandler);

        http.oauth2Login();
        http.oauth2Client();
        http.csrf().disable();
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
