package com.inyaa.web.config.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/3/15 23:30
 */
@Data
@Entity
@Table(name="sys_social_config")
public class SysSocialConfig {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 配置的key
     */
    private String configKey;

    /**
     * 图标的链接
     */
    private String icon;

    /**
     * 点击跳转的链接
     */
    private String url;

    /**
     * 是否开启
     */
    private boolean isOpen;
}
