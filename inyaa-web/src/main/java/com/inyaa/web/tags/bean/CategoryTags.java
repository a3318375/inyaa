//package com.inyaa.web.tags.bean;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
///**
// * @author byteblogs
// * @since 2019-08-28
// */
//@Data
//@Entity
//@EqualsAndHashCode(callSuper = false)
//@Table("inyaa_category_tags")
//public class CategoryTags {
//
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    /**
//     * 名称
//     */
//    private Long tagsId;
//
//    /**
//     * 分类的主键
//     */
//    private Long categoryId;
//
//    /**
//     * 排序
//     */
//    private Integer sort;
//
//    /**
//     * 创建时间
//     */
//    private LocalDateTime createTime;
//
//    /**
//     * 更新时间
//     */
//    private LocalDateTime updateTime;
//
//}
