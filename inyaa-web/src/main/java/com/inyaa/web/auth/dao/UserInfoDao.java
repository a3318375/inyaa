package com.inyaa.web.auth.dao;


import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.dsl.UserDslDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoDao extends JpaRepository<UserInfo, Integer>, UserDslDao {

    UserInfo findByUsername(String username);

    UserInfo getByUsername(String username);

    UserInfo getByRoleId(Integer roleId);

}
