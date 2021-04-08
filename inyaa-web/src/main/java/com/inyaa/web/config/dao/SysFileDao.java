package com.inyaa.web.config.dao;

import com.inyaa.web.config.bean.SysFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: yuxh
 * @date: 2021/3/12 1:16
 */
public interface SysFileDao extends JpaRepository<SysFile, Integer> {

    @Query(value = "select * FROM sys_file where type = ?1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    SysFile getRandImg(int type);
}
