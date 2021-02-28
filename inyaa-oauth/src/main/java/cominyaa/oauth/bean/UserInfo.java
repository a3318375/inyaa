package cominyaa.oauth.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_user")
public class UserInfo {

    @Id
    @GeneratedValue
    private Integer id;

    private String username; // 用户名
    private String password; // 密码
    private String name;// 姓名
    private String email; // 邮箱
    private Date loginDate;// 最后登录日期
    private String loginIp;// 最后登录IP
    private boolean accountNonExpired; // 账号是否未过期
    private boolean accountNonLocked; // 账号是否未锁定
    private boolean credentialsNonExpired; // 账号凭证是否未过期
    private boolean enabled; // 账号是否可用

}
