package cominyaa.oauth.config;

import cominyaa.oauth.jwt.CustomSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.event.LoggerListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomSecurityProperties customSecurityProperties;

    /**
     * 配置URL访问授权,必须配置authorizeRequests(),否则启动报错,说是没有启用security技术。
     * 注意:在这里的身份进行认证与授权没有涉及到OAuth的技术：当访问要授权的URL时,请求会被DelegatingFilterProxy拦截,
     * 如果还没有授权,请求就会被重定向到登录界面。在登录成功(身份认证并授权)后,请求被重定向至之前访问的URL。
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/oauth/login")
                .loginProcessingUrl("/login")
                //.defaultSuccessUrl("/")
                .failureForwardUrl("/oauth/login?error=true")
                .permitAll(); //登记界面，默认是permitAll

        http.authorizeRequests()
                .antMatchers(customSecurityProperties.getPermitAll()).permitAll() //不用身份认证可以访问
                .mvcMatchers("/.well-known/jwks.json").permitAll() //开放JWK SET端点，提供给资源服务器访问获取公钥信息
                .anyRequest().authenticated(); //其它的请求要求必须有身份认证
        http.csrf().disable();
        //开启跨域
        http.cors();
        // session管理
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    /**
     * 支持 password 模式(配置)
     *
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 指定不拦截的路径规则
     */
    @Override
    public void configure(WebSecurity web) {
        //设置不需要拦截的路径，也就是不需要认证的路径
        web.ignoring().antMatchers(customSecurityProperties.getIgnoring());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
