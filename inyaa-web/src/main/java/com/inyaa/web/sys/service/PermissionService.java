package com.inyaa.web.sys.service;

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

    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
