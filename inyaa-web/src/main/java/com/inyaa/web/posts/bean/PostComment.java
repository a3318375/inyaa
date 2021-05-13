package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_comment")
@Data
public class PostComment extends BaseVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 评论的目标用户id
     */
    private Integer toUserId;

    /**
     * 0-评论博客，1-评论用户
     */
    private Integer type;

    /**
     * 评论的父节点id
     */
    private Integer pid;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 文章id
     */
    private Integer postId;

    /**
     * 回复时间
     */
    private LocalDateTime createTime;
}
