package com.inyaa.web.config;

import com.inyaa.web.auth.service.RoleInfoService;
import com.inyaa.web.sys.bean.Permission;
import com.inyaa.web.sys.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: yuxh
 * @date: 2021/4/5 23:44
 */
@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisService redisService;
    private final PermissionService permissionService;
    private final RoleInfoService roleInfoService;

    public Map<String, Collection<ConfigAttribute>> getConfigAttributeMap() {
        String key = "CONFIG_ATTRIBUTE";
        Map<String, Collection<ConfigAttribute>> map = redisService.authMapGet(key);
        if (map == null || map.size() < 1) {
            map = new HashMap<>();
            List<Permission> permissionList = permissionService.findAll();
            for (Permission permission : permissionList) {
                List<String> roleList = roleInfoService.findRoleKeyList(permission.getId());
                List<ConfigAttribute> configAttributeList = new ArrayList<>();
                for (String roleKey : roleList) {
                    ConfigAttribute configAttribute = new SecurityConfig(roleKey);
                    configAttributeList.add(configAttribute);
                }
                map.put(permission.getUrl(), configAttributeList);
                redisService.authMapSet(key, map);
            }
        }
        return map;
    }
}
