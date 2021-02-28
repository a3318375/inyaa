package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "inyaa_posts_attribute")
@Data
public class PostsAttribute {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Long id;

    private String content;

    private Long postsId;
}
