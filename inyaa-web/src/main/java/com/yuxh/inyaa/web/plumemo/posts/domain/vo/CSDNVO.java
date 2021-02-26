package com.yuxh.inyaa.web.plumemo.posts.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import us.codecraft.webmagic.model.annotation.ExtractBy;

/**
 * @author: zsg
 * @description:
 * @date: 2019/8/3 13:15
 * @modified:
 */

@Data
@Accessors(chain = true)
public class CSDNVO {

    @ExtractBy("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1/text()")
    private String title;

    @ExtractBy("//*[@id='content_views']")
    private java.util.List<String> content;

}
