package com.inyaa.web.posts.dao;

import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.dsl.PostInfoDslDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostInfoDao extends JpaRepository<PostInfo, Integer>, PostInfoDslDao {

}
