package com.inyaa.base.auth.dao;


import com.inyaa.base.auth.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findByUsername(String username);

    UserInfo getByUsername(String username);

    UserInfo getByRoleId(Integer roleId);
}
