package cominyaa.oauth.service;

import cominyaa.oauth.bean.UserInfo;
import cominyaa.oauth.dao.RoleInfoRepository;
import cominyaa.oauth.dao.UserInfoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: yuxh
 * @date: 2021/2/28 3:01
 */
@Service
public class UserInfoService implements UserDetailsService {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private RoleInfoRepository roleInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            //List<String> roles = roleInfoRepository.findRoleKeyByUserId(user.getId());
            List<String> roles = new ArrayList<>();
            return buildUserForAuthentication(user, buildUserAuthority(roles));
        }
    }

    private User buildUserForAuthentication(UserInfo user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<String> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();

        for (String userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole));
        }
        return new ArrayList<>(setAuths);
    }
}
