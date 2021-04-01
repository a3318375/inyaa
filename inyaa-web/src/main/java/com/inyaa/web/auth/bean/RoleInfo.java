package com.inyaa.web.auth.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="sys_role")
public class RoleInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String roleName;

    private String roleKey;

    private String remark;

    private LocalDateTime createTime;
}
