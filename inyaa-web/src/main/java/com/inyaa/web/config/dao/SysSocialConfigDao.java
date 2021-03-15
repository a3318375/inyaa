package com.inyaa.web.config.dao;

import com.inyaa.web.config.bean.SysSocialConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yuxh
 * @date: 2021/3/15 23:33
 */
public interface SysSocialConfigDao extends JpaRepository<SysSocialConfig, Integer> {
}
