package com.inyaa.web.posts.dao;


import com.inyaa.web.posts.bean.PostArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author byteblogs
 * @since 2019-08-28
 */
public interface PostArticleDao extends JpaRepository<PostArticle, Integer> {

    @Query("select u.context from PostArticle u where u.postId = ?1")
    String getContextByPostId(Integer id);
}
