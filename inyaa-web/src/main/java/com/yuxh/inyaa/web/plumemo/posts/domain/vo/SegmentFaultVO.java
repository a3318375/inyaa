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
public class SegmentFaultVO {

    @ExtractBy("//*[@id='articleTitle']/a/text()")
    private String title;

    @ExtractBy("/html/body/div[3]/div[2]/div/div[1]/div[3]//*")
    private java.util.List<String> content;

}
