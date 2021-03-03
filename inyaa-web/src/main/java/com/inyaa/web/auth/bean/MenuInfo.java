package com.inyaa.web.auth.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/3 23:45
 */
@Data
@Entity
@Table(name="sys_menu")
public class MenuInfo {

    @Id
    @GeneratedValue
    private Integer id; //主键

    private Integer pid; //父id

    private String name; //名称

    private String path; //路径

    private String icon; //图标

    private Integer sort; //排序

    private Integer open; //是否开启（0关 1开）

    private Integer type; //菜单类型 （0菜单 1按钮

    private LocalDateTime createTime; //创建时间

    private LocalDateTime updateTime; //更新时间
}
