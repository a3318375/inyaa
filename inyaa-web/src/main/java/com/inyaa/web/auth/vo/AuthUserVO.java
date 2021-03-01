package com.inyaa.web.auth.vo;

import com.inyaa.web.posts.bean.BaseVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author byteblogs
 * @since 2019-08-28
 */
@Data
@Accessors(chain = true)
public class AuthUserVO extends BaseVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 社交账户ID
     */
    private String socialId;

    /**
     * 用户名
     */
    private String name;

    private String username;
    /**
     * 密码
     */
    private String password;

    private String passwordOld;

    private String verifyCode;

    /**
     * 角色主键 1 普通用户 2 admin
     */
    private Long roleId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    private String token;

    private List<String> roles;

    private String introduction;

    private Boolean status;

    /**
     * 邮箱
     */
    private String email;

    private String accessKey;

    private String secretKey;

}
