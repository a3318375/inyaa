package com.inyaa.web.config.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/3/13 0:53
 */
@Data
@Entity
@Table(name="sys_site_config")
public class SysSiteConfig {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    private String configKey;

    private String configValue;
}
