package com.inyaa.web.posts.dsl;

import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.dto.PostInfoDto;
import com.inyaa.web.posts.vo.PostArchiveVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/2/27 14:43
 */
public interface PostInfoDslDao {

    Page<PostInfo> findPostListPage(PostInfoDto req);

    List<PostArchiveVo> findArchiveList();

    List<PostInfo> findByArchiveDate(String archiveDate);
}
