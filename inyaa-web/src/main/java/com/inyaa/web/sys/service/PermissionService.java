package com.inyaa.web.sys.service;

import com.inyaa.web.config.RedisService;
import com.inyaa.web.sys.bean.Permission;
import com.inyaa.web.sys.dao.PermissionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/4/5 11:06
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionDao permissionDao;
    private final RedisService redisService;

    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    public void delete(Permission permission) {
        permissionDao.deleteById(permission.getId());
        String key = "CONFIG_ATTRIBUTE";
        redisService.del(key);
    }

    public void save(Permission permission) {
        permissionDao.save(permission);
        String key = "CONFIG_ATTRIBUTE";
        redisService.del(key);
    }
}
