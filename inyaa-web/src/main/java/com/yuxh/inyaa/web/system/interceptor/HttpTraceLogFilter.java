package com.yuxh.inyaa.web.system.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class HttpTraceLogFilter extends OncePerRequestFilter implements Ordered {
    private final DateTimeFormatter parse = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String[] INHERENT_ESCAPE_URIS = {"/js","/static", "/upload", "/favicon", ".css",
            "/getCaptcha", "JSESSIONID=", ".js", ".png", ".jpg"};

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(isIgnoreUrl(request)){
            filterChain.doFilter(request, response);
            return;
        }
        if (!isRequestValid(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
            status = response.getStatus();
        } finally {
            String path = request.getRequestURI();
            long latency = System.currentTimeMillis() - startTime;
            log.info(LocalDateTime.now().format(parse) + ",path:" + path
                    + ", method:" + request.getMethod()
                    + ", params:" + request.getParameterMap()
                    + ", time:" + latency
                    + ", staus:" + status);
            updateResponse(response);
        }
    }

    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException ex) {
            return false;
        }
    }


    private boolean isIgnoreUrl(HttpServletRequest request) {
        try {
            return Arrays.stream(INHERENT_ESCAPE_URIS).anyMatch(o -> request.getRequestURI().indexOf(o)!=-1);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 解决字节流读取一次后消失。
     */
    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }

}
