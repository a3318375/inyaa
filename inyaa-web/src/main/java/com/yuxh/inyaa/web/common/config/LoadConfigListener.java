package com.yuxh.inyaa.web.common.config;

import com.byteblogs.common.cache.ConfigCache;
import com.byteblogs.common.context.BeanTool;
import com.byteblogs.plumemo.config.dao.ConfigDao;
import com.byteblogs.plumemo.config.domain.po.Config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
//@DependsOn({"dataSource"})
public class LoadConfigListener {

    //    @PostConstruct
    public void init() {
        final ConfigDao configDao = BeanTool.getBean(ConfigDao.class);
        final java.util.List<Config> configList = configDao.selectList(null);
        configList.forEach(config -> {
            log.debug("config_key: {}, config_vlaue: {}", config.getConfigKey(), config.getConfigValue());
            ConfigCache.putConfig(config.getConfigKey(), config.getConfigValue());
        });
    }

}
