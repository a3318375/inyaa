package com.inyaa.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inyaa.web.exception.AjaxResponse;
import com.inyaa.web.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper; //springmvc启动时自动装配json处理类

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        e.printStackTrace();
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", 403);
        body.put("error", "Forbidden");
        body.put("message", e.getMessage());
        body.put("path", request.getRequestURI());

        AjaxResponse ajaxResponse = AjaxResponse.error(new CustomException(HttpStatus.FORBIDDEN, "抱歉，您没有访问该接口的权限", body));
        response.setStatus(403);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(ajaxResponse));
        }
    }

}
