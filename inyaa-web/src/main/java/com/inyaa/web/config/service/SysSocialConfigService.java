package com.inyaa.web.config.service;

import com.inyaa.web.config.bean.SysSocialConfig;
import com.inyaa.web.config.dao.SysSocialConfigDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/15 23:33
 */
@Service
@RequiredArgsConstructor
public class SysSocialConfigService {

    private final SysSocialConfigDao sysSocialConfigDao;

    public List<SysSocialConfig> list() {
        return sysSocialConfigDao.findAll();
    }
}
