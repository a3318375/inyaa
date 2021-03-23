package com.inyaa.resource.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>重写访问拒绝拦截器</h1>
 * Created by hanqf on 2020/11/3 17:07.
 * <p>
 * 登录验证成功，即token合法，但是没有权限访问资源时执行
 */

@Component
@Slf4j
public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        Map<String, String> rsp = new HashMap<>();
        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        rsp.put("code", HttpStatus.UNAUTHORIZED.value() + "");
        rsp.put("msg", "无权访问");

        log.error("无权访问", accessDeniedException);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(rsp));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
