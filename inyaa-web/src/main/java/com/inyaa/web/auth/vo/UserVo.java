package com.inyaa.web.auth.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: yuxh
 * @date: 2021/3/31 0:06
 */
@Data
public class UserVo {

    private Integer id;
    private String username; // 用户名
    private String password; // 密码
    private String name;// 姓名
    private String email; // 邮箱
    private Date loginDate;// 最后登录日期
    private String loginIp;// 最后登录IP
    private String avatar; //头像
    private String socialId; //社交账号id
    private Integer roleId; //角色主键 1 普通用户 2 admin
    private Integer deptId; //部门id
    private boolean accountNonExpired; // 账号是否未过期
    private boolean accountNonLocked; // 账号是否未锁定
    private boolean credentialsNonExpired; // 账号凭证是否未过期
    private boolean enabled; // 账号是否可用
    private String remark; //备注
    private LocalDateTime createTime;
    private String roleName;

}
