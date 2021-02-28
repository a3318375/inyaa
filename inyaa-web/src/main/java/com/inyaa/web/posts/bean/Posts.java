package com.inyaa.web.posts.bean;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inyaa_posts")
@Data
public class Posts {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 封面图
     */
    private String thumbnail;

    /**
     * 评论数
     */
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
    private Integer views;

    /**
     * 文章权重
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long authorId;

    /**
     * 是否打开评论
     */
    private Integer isComment;

    /**
     * 同步到byteblogs状态 (0 未同步 或者同步失败的状态 1同步已成功)
     */
    private Integer syncStatus;

    /**
     * 文章分类Id
     */
    private Integer categoryId;
}
