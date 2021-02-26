package com.yuxh.inyaa.web.common.config;

import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:byteblogs
 * @Date:2018/09/27 12:52
 */
@Configuration
@MapperScan("com.byteblogs.plumemo.*.dao")
public class MybatisPlusConfig {

    @org.springframework.context.annotation.Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @org.springframework.context.annotation.Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }
}