package com.inyaa.base.auth.dao;

import com.inyaa.base.auth.bean.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleInfoRepository extends JpaRepository<RoleInfo, Integer> {

    @Query(value = "select role_key from sys_role where id in (select role_id from sys_user_role where user_id = ?1)", nativeQuery = true)
    List<String> findRoleKeyByUserId(Integer userId);
}
