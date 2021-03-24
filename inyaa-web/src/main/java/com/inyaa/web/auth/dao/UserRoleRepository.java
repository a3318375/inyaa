package com.inyaa.web.auth.dao;

import com.inyaa.web.auth.bean.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
