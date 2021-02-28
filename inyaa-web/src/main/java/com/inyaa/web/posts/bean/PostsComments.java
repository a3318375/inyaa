package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inyaa_posts_comments")
@Data
public class PostsComments {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Long id;

    private Long authorId;

    private String content;

    private Long parentId;

    private Integer status;

    private Long postsId;

    /**
     * 层级结构
     */
    private String treePath;

    private LocalDateTime createTime;
}
