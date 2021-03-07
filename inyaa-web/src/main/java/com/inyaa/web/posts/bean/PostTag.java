package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: yuxh
 * @date: 2021/3/5 23:56
 */
@Entity
@Table(name = "post_tag")
@Data
public class PostTag {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    private Integer postId;

    private Integer tagId;
}
