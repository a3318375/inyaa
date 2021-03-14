package com.inyaa.web.posts.vo;

import com.inyaa.web.posts.bean.PostInfo;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 * @author byteblogs
 * @since 2019-08-28
 */
@Data
public class PostsAdminVO extends PostInfo {

    private List<Integer> tagList;

    private String context;

}
