package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.PostComment;
import com.inyaa.web.posts.dao.PostCommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * @author: yuxh
 * @date: 2021/3/16 0:47
 */
@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentDao postCommentDao;

    public BaseResult<Page<PostComment>> list(PostComment req) {
        Sort sort = Sort.by("createTime").descending();
        Pageable page = PageRequest.of(req.getPage(), req.getSize(), sort);

        Example<PostComment> example = Example.of(req);
        Page<PostComment> list = postCommentDao.findAll(example, page);
        return BaseResult.success(list);
    }

    public void save(PostComment req) {
        postCommentDao.save(req);
    }
}
