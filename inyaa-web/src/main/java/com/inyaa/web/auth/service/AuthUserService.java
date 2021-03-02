package com.inyaa.web.auth.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.base.enums.RoleEnum;
import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.dao.UserInfoRepository;
import com.inyaa.web.auth.vo.AuthUserVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * @author byteblogs
 * @since 2019-08-28
 */
@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final UserInfoRepository userInfoRepository;

    public BaseResult<AuthUserVO> getUserInfo(String username) {
        UserInfo userInfo = userInfoRepository.getByUsername(username);
        return BaseResult.success(new AuthUserVO()
                .setRoles(Collections.singletonList(RoleEnum.getEnumTypeMap().get(userInfo.getRoleId()).getRoleName()))
                .setName(userInfo.getName())
                .setAvatar(userInfo.getAvatar())
                .setEmail(userInfo.getEmail()))
                .setSuccess(userInfo.isAccountNonLocked());

    }

    public BaseResult<AuthUserVO> getMasterUserInfo() {
        UserInfo userInfo = userInfoRepository.getByRoleId(RoleEnum.ADMIN.getRoleId());
        AuthUserVO authUserVO = new AuthUserVO();
        if (userInfo != null) {
            authUserVO.setName(userInfo.getName())
                    .setEmail(userInfo.getEmail())
                    .setAvatar(userInfo.getAvatar());
        }

        return BaseResult.success(authUserVO);
    }

    public BaseResult<Page<UserInfo>> getUserList(AuthUserVO authUserVO) {
        Pageable page = PageRequest.of(authUserVO.getPage(), authUserVO.getSize());
        ExampleMatcher matcher = ExampleMatcher.matching();
        UserInfo req = new UserInfo();
        if (StringUtils.isNotBlank(authUserVO.getKeywords())) {
            matcher.withMatcher("name" ,ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (authUserVO.getStatus() != null) {
            req.setAccountNonLocked(authUserVO.getStatus());
        }
        Example<UserInfo> ex = Example.of(req, matcher);
        Page<UserInfo> records = userInfoRepository.findAll(ex, page);
        return BaseResult.success(records);
    }

    public BaseResult<String> deleteUsers(Integer id) {
        userInfoRepository.deleteById(id);
        return BaseResult.success();
    }

    public BaseResult<String> register(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
        return BaseResult.success();
    }
}
