package com.inyaa.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper; //springmvc启动时自动装配json处理类

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