package com.inyaa.web.posts.dsl;

import com.inyaa.web.posts.bean.PostComment;
import com.inyaa.web.posts.vo.PostCommentVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostCommentDslDao {

    public Page<PostCommentVO> findPostCommentListPage(PostComment req);

    public List<PostCommentVO> findPostCommentList(PostComment req);
}
