package com.inyaa.web.posts.vo;

import com.inyaa.web.posts.bean.TagInfo;
import lombok.Data;

@Data
public class TagVo extends TagInfo {
    private long count = 0;
}
