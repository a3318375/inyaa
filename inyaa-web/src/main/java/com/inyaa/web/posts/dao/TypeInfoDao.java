package com.inyaa.web.posts.dao;


import com.inyaa.web.posts.bean.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: yuxh
 * @date: 2021/3/5 21:29
 */
public interface TypeInfoDao extends JpaRepository<TypeInfo, Integer> {
}
