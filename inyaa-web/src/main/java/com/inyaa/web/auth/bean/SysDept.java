package com.inyaa.web.auth.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/27 18:50
 */
@Data
@Entity
@Table(name="sys_dept")
public class SysDept {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private boolean enable;

    private String name;

    private Integer parentId;

    private Integer sort;

    private String remark;

    private LocalDateTime createTime;
}
