package com.inyaa.web.sys.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/4/5 9:52
 */
@Data
@Entity
@Table(name="sys_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String url;

    private LocalDateTime createTime;
}
