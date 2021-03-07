package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/3/5 16:07
 */
@Entity
@Table(name = "post_article")
@Data
public class PostArticle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 文章id
     */
    private Integer postId;

    /**
     * 文章内容
     */
    private String context;
}
