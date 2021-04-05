package com.inyaa.web.sys.dao;

import com.inyaa.web.sys.bean.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yuxh
 * @date: 2021/4/5 11:05
 */
public interface RolePermissionDao extends JpaRepository<RolePermission, Integer> {
}
