package com.inyaa.web.config.bean;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/29 23:14
 */
@Data
@Entity
@Table(name="sys_file")
public class SysFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    private Integer id;

    /**
     * 图片链接
     */
    private String url;

    /**
     * 0-封面
     */
    private Integer type;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;
}
