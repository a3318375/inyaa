package com.inyaa.security.bean;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author: yuxh
 * @date: 2021/2/28 3:05
 */
public class InyaaUser extends User {
    /**
     * 用户ID
     */
    @Getter
    private Integer id;

    public InyaaUser(Integer id, String username, String password, boolean enabled,
                     boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

}
