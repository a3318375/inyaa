package com.inyaa.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;

/**
 * @author: yuxh
 * @date: 2021/2/27 23:09
 */
@EnableWebSecurity
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;
    @Resource
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/webjars/**");

    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests((authorizeRequests) ->
//                        authorizeRequests
//                                .antMatchers(HttpMethod.GET, "/message/**").hasAuthority("SCOPE_message:read")
//                                .antMatchers(HttpMethod.POST, "/message/**").hasAuthority("SCOPE_message:write")
//                                .anyRequest().authenticated().and()
//                )
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//
//        http.oauth2Client();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(oAuth2AccessDeniedHandler)
                .and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        http.authorizeRequests().antMatchers("/authorize/**").permitAll().anyRequest().authenticated()
                .and()
                .oauth2Client().authorizedClientService(oAuth2AuthorizedClientService);
    }

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcOperations jdbcOperations, ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository);
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
