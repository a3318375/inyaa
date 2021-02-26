package com.yuxh.inyaa.web.plumemo.posts.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import us.codecraft.webmagic.model.annotation.ExtractBy;

/**
 * @description:
 * @author: byteblogs
 * @date: 2019/09/03 18:49
 */
@Data
@Accessors(chain = true)
public class CNBlogsVO {

    @ExtractBy("//*[@id=\"cb_post_title_url\"]/text()")
    private String title;

    @ExtractBy("//*[@id=\"cnblogs_post_body\"]")
    private java.util.List<String> content;
}
