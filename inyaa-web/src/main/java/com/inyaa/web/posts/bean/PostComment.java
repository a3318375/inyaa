package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_comment")
@Data
public class PostComment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 用户id，逐渐
     */
    private Integer userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 文章id
     */
    private Long postId;

    /**
     * 回复时间
     */
    private LocalDateTime createTime;
}
