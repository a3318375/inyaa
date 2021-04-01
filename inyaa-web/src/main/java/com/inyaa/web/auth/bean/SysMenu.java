package com.inyaa.web.auth.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/3 23:45
 */
@Data
@Entity
@Table(name="sys_menu")
public class SysMenu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id; //主键

    private Integer pid; //父id

    private String name; //名称

    private String permission; //权限标识

    private String component; //组件地址

    private Integer type; //0-目录，1-菜单

    private String path; //路径

    private String icon; //图标

    private Integer sort; //排序

    private Integer enable; //是否开启（0关 1开）

    private LocalDateTime createTime; //创建时间

    private LocalDateTime updateTime; //更新时间
}
