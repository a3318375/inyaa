package com.inyaa.web.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.inyaa.base.auth.bean.UserInfo;
import com.inyaa.base.auth.dao.UserInfoRepository;
import com.inyaa.base.bean.BaseResult;
import com.inyaa.base.enums.RoleEnum;
import com.inyaa.web.auth.dao.AuthTokenDao;
import com.inyaa.web.auth.vo.AuthUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author byteblogs
 * @since 2019-08-28
 */
@Service
public class AuthUserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AuthTokenDao authTokenDao;

    public BaseResult<AuthUserVO> getUserInfo(String username) {
        UserInfo userInfo = userInfoRepository.getByUsername(username);
        return BaseResult.success(new AuthUserVO()
                .setRoles(Collections.singletonList(RoleEnum.getEnumTypeMap().get(userInfo.getRoleId()).getRoleName()))
                .setName(userInfo.getName())
                .setAvatar(userInfo.getAvatar())
                .setEmail(userInfo.getEmail()))
                .setStatus(userInfo.isAccountNonLocked());

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

    public BaseResult<String> updateAdmin(AuthUserVO authUserVO) {
        UserInfo info = userInfoRepository.getByUsername(authUserVO.getUsername());
        info.setEmail(authUserVO.getEmail())
                .setAvatar(authUserVO.getAvatar())
                .setName(authUserVO.getName());
        userInfoRepository.save(info);
        return BaseResult.success();
    }

    public BaseResult updateUser(AuthUserVO authUserVO) {
        authUserDao.updateById(new AuthUser()
                .setId(authUserVO.getId())
                .setEmail(authUserVO.getEmail())
                .setAvatar(authUserVO.getAvatar())
                .setName(authUserVO.getName())
                .setIntroduction(authUserVO.getIntroduction())
                .setStatus(authUserVO.getStatus())
        );

        // 锁定了账户，强制用户下线
        if (authUserVO.getStatus() == Constants.ONE) {
            authTokenDao.delete(new LambdaQueryWrapper<AuthToken>().eq(AuthToken::getUserId, authUserVO.getId()));
        }
        return BaseResult.success();
    }

    public BaseResult<String> saveAuthUserStatus(AuthUserVO authUserVO) {
        if (authUserVO.getStatus() != null
                && authUserVO.getId() != null
                && authUserDao.selectCount(new LambdaQueryWrapper<AuthUser>()
                .eq(AuthUser::getId, authUserVO.getId()).eq(AuthUser::getRoleId, Constants.TWO)) == 0) {
            authUserDao.updateById(new AuthUser().setId(authUserVO.getId()).setStatus(authUserVO.getStatus()));
            return Result.createWithSuccessMessage();
        }
        return BaseResult.success();
    }

    public BaseResult<String> deleteUsers(Integer id) {
        userInfoRepository.deleteById(id);
        return BaseResult.success();
    }
}
