package com.inyaa.web.config.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/3/14 1:57
 */
@Data
@Entity
@Table(name="sys_media_config")
public class SysMediaConfig {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 图片链接
     */
    private String url;

    /**
     * 图片类型，0-大图，1-小图
     */
    private Integer type;


}
