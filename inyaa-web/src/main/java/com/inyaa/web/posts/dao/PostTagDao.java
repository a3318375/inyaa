package com.inyaa.web.posts.dao;

import com.inyaa.web.posts.bean.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/5 21:28
 */
public interface PostTagDao extends JpaRepository<PostTag, Integer> {

    @Query("select u.tagId from PostTag u where u.postId = ?1")
    List<Integer> findTagIdByPostId(Integer id);

    @Modifying
    @Query("delete from PostTag u where u.postId = ?1")
    void deleteByPostId(int id);
}
