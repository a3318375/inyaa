package com.inyaa.web.config.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/3/12 1:13
 */
@Data
@Entity
@Table(name="sys_oss_config")
public class SysOssConfig {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    private Integer type;

    private String aesKey;

    private String secretKey;

    private String bucket;

    private String url;

    private String path;

    private String defined;
}
