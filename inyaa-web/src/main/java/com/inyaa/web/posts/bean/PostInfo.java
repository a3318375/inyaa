package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/5 15:32
 */
@Entity
@Table(name = "post_info")
@Data
public class PostInfo extends BaseVo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 封面图
     */
    private String cover;

    /**
     * 评论数
     */
    @Column(name = "comments", insertable = false, updatable = false)
    private Integer comments;

    /**
     * 状态 1 草稿 2 发布
     */
    private Integer status;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 浏览次数
     */
    @Column(name = "views", insertable = false, updatable = false)
    private Integer views;

    /**
     * 创建时间
     */
    @Column(name = "create_time", insertable = false, updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户id，作者
     */
    private Integer userId;

    /**
     * 是否打开评论
     */
    private Boolean isComment;

    /**
     * 文章分类Id
     */
    private Integer typeId;
}
