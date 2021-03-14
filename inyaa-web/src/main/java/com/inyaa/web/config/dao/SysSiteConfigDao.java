package com.inyaa.web.config.dao;

import com.inyaa.web.config.bean.SysSiteConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: yuxh
 * @date: 2021/3/13 0:55
 */
public interface SysSiteConfigDao extends JpaRepository<SysSiteConfig, Integer> {

    @Query(value = "select configValue from SysSiteConfig where configKey = 'music'")
    public String getMusicConfig();

}
