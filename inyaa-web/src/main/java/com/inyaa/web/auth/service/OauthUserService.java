package com.inyaa.web.auth.service;

import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.bean.UserRole;
import com.inyaa.web.auth.dao.RoleInfoRepository;
import com.inyaa.web.auth.dao.UserInfoDao;
import com.inyaa.web.auth.dao.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OauthUserService extends DefaultOAuth2UserService {

    private final UserInfoDao userInfoDao;

    private final RoleInfoRepository roleInfoRepository;

    private final UserRoleRepository userRoleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(oAuth2UserRequest);
        if ("github".equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
            Map<String, Object> attributes = user.getAttributes();
            UserInfo userInfo = userInfoDao.getByUsername(String.valueOf(attributes.get("id")));
            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo.setAccountNonLocked(true);
                userInfo.setAccountNonLocked(true);
                userInfo.setCredentialsNonExpired(true);
                userInfo.setEnabled(true);
                userInfo.setAvatar(String.valueOf(attributes.get("avatar_url")));
                userInfo.setUsername(String.valueOf(attributes.get("id")));
                userInfo.setName(String.valueOf(attributes.get("login")));
                userInfoDao.save(userInfo);
            }
            List<String> roleList = roleInfoRepository.findRoleKeyByUserId(userInfo.getId());
            if (roleList.size() < 1) {
                UserRole ur = new UserRole();
                ur.setUserId(userInfo.getId());
                ur.setRoleId(1);
                userRoleRepository.save(ur);
                roleList.add("SCOPE_any");
            }
            Set<GrantedAuthority> authoritySet = new HashSet<>(user.getAuthorities());
            String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails()
                    .getUserInfoEndpoint().getUserNameAttributeName();

            for (String str : roleList) {
                authoritySet.add(new SimpleGrantedAuthority(str));
            }
            return new DefaultOAuth2User(authoritySet, attributes, userNameAttributeName);
        } else {
            return user;
        }
    }
}
