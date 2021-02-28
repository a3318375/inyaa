package com.inyaa.web.posts.bean;

import lombok.Data;

@Data
public class BaseVo {

    private Long id;
    /**
     * 关键词搜索
     */
    protected String keywords;

    private Integer page = 0;

    private Integer size = 10;
}
