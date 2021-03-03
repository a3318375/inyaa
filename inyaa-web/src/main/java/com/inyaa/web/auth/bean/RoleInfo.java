package com.inyaa.web.auth.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="inyaa_sys_role")
public class RoleInfo {

    @Id
    @GeneratedValue
    private Integer id;

    private String roleName;

    private String roleKey;

    private String description;

}
