package com.inyaa.web.posts.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/5 16:14
 */
@Entity
@Table(name = "tag_info")
@Data
public class TagInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
