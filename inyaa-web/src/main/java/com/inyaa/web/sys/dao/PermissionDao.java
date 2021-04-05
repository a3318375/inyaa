package com.inyaa.web.sys.dao;

import com.inyaa.web.sys.bean.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yuxh
 * @date: 2021/4/5 11:04
 */
public interface PermissionDao extends JpaRepository<Permission, Integer> {
}
