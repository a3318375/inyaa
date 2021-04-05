package com.inyaa.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

/**
 * @author: yuxh
 * @date: 2021/4/5 8:45
 */
@Service
@RequiredArgsConstructor
public class InyaaFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final CacheService cacheService;

    /***
     * 返回该url所需要的用户权限信息
     *
     * @param object: 储存请求url信息
     * @return: null：标识不需要任何权限都可以访问
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        Map<String, Collection<ConfigAttribute>> cacheMap = cacheService.getConfigAttributeMap();
        for (String url : cacheMap.keySet()) {
            if (new AntPathRequestMatcher(url).matches(request)) {
                return cacheMap.get(url);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
