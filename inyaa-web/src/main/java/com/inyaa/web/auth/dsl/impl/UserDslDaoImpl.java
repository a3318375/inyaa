package com.inyaa.web.auth.dsl.impl;

import com.inyaa.web.auth.bean.QRoleInfo;
import com.inyaa.web.auth.bean.QUserInfo;
import com.inyaa.web.auth.dsl.UserDslDao;
import com.inyaa.web.auth.vo.UserVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/31 0:10
 */
@Repository
@RequiredArgsConstructor
public class UserDslDaoImpl implements UserDslDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserVo> findUserList() {
        QUserInfo userInfo = QUserInfo.userInfo;
        QRoleInfo roleInfo = QRoleInfo.roleInfo;
        JPAQuery<UserVo> jpaQuery = jpaQueryFactory
                .select(Projections.bean(UserVo.class, userInfo.id, userInfo.username, userInfo.name, userInfo.avatar, userInfo.createTime, userInfo.email, userInfo.enabled, roleInfo.roleName))
                .from(userInfo).leftJoin(roleInfo).on(userInfo.roleId.eq(roleInfo.id))
                .orderBy(userInfo.createTime.desc());
        return jpaQuery.fetch();
    }
}
