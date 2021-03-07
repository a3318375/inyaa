package com.inyaa.web.posts.dto;

import com.inyaa.web.posts.bean.PostInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/5 21:59
 */
@Data
public class PostInfoDto extends PostInfo {

    private Integer tagId;

    private List<Integer> tagList;

    private String context;
}
