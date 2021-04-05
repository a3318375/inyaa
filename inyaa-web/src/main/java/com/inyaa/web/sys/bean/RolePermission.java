package com.inyaa.web.sys.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/4/5 11:03
 */
@Data
@Entity
@Table(name="sys_role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer roleId;

    private Integer permissionId;

    private Integer createTime;

}
