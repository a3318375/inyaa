package com.inyaa.web.posts.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseVo {

    /**
     * 关键词搜索
     */
    protected String keywords;

    private Integer page = 1;

    private Integer size = 10;
}
