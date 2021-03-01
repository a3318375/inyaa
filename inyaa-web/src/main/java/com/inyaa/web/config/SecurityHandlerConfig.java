package com.inyaa.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2018年4月5日 下午19:52:21
 * 类说明
 */
@Component
@Configuration
public class SecurityHandlerConfig {

    @Resource
    private ObjectMapper objectMapper; //springmvc启动时自动装配json处理类


    /**
     * 未登录，返回401
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {

            Map<String, String> rsp = new HashMap<>();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            rsp.put("code", HttpStatus.UNAUTHORIZED.value() + "");
            rsp.put("msg", "无效的token.");

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(rsp));
            response.getWriter().flush();
            response.getWriter().close();

        };
    }


}
