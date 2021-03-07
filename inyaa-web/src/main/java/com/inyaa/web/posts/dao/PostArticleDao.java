package com.inyaa.web.posts.dao;


import com.inyaa.web.posts.bean.PostArticle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author byteblogs
 * @since 2019-08-28
 */
public interface PostArticleDao extends JpaRepository<PostArticle, Integer> {

}
