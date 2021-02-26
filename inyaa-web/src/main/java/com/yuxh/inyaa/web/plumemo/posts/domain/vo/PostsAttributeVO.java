package com.yuxh.inyaa.web.plumemo.posts.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author byteblogs
 * @since 2019-08-28
 */
@Data
@Accessors(chain = true)
public class PostsAttributeVO {

    private Long id;

    private String content;

}
