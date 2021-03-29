package com.inyaa.web.config.dao;

import com.inyaa.web.config.bean.SysFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yuxh
 * @date: 2021/3/12 1:16
 */
public interface SysFileDao extends JpaRepository<SysFile, Integer> {
}
