package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inyaa_posts_tags")
@Data
public class PostsTags {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Long id;

    /**
     * 名称
     */
    private Long tagsId;

    /**
     * 文章主键
     */
    private Long postsId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
