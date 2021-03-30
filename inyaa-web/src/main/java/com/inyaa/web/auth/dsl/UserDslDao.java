package com.inyaa.web.auth.dsl;

import com.inyaa.web.auth.vo.UserVo;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/31 0:09
 */
public interface UserDslDao {

    List<UserVo> findUserList();

}
