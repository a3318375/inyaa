package com.inyaa.web.posts.vo;

import com.inyaa.web.posts.bean.PostInfo;
import lombok.Data;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/15 0:27
 */
@Data
public class PostArchiveVo {

    private String archiveDate;

    private Integer articleTotal;

    private List<PostInfo> archivePosts;

}
