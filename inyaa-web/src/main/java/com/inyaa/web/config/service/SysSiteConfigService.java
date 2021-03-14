package com.inyaa.web.config.service;

import com.inyaa.web.config.bean.SysSiteConfig;
import com.inyaa.web.config.dao.SysSiteConfigDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yuxh
 * @date: 2021/3/13 0:55
 */
@Service
@RequiredArgsConstructor
public class SysSiteConfigService {

    private final SysSiteConfigDao sysSiteConfigDao;

    public Map<String, String> getConfigMap() {
        Map<String, String> config = new HashMap<>();
        List<SysSiteConfig> list = sysSiteConfigDao.findAll();
        for (SysSiteConfig bean : list) {
            config.put(bean.getConfigKey(), bean.getConfigValue());
        }
        return config;
    }

}
