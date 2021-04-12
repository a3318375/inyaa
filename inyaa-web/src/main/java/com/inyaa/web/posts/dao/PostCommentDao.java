package com.inyaa.web.posts.dao;

import com.inyaa.web.posts.bean.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yuxh
 * @date: 2021/3/5 21:28
 */
public interface PostCommentDao extends JpaRepository<PostComment, Integer> {

}
