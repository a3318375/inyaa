package com.inyaa.web.posts.dao;

import com.inyaa.web.posts.bean.TagInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yuxh
 * @date: 2021/3/6 0:01
 */
public interface TagInfoDao extends JpaRepository<TagInfo, Integer> {
}
