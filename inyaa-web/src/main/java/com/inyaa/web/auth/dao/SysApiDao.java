package com.inyaa.web.auth.dao;

import com.inyaa.web.auth.bean.SysApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/12 23:17
 */
public interface SysApiDao extends JpaRepository<SysApi, Integer> {

    @Query(value = "select url from SysApi where allowAccess = TRUE")
    List<String> findUrlByAllowAccess();
}
