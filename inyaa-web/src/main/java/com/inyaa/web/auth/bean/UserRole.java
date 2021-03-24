package com.inyaa.web.auth.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/3/24 22:23
 */
@Data
@Entity
@Table(name="sys_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer roleId;

    private Integer userId;
}
