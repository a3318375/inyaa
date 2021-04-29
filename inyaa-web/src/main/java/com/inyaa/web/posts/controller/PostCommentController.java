package com.inyaa.web.posts.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.PostComment;
import com.inyaa.web.posts.service.PostCommentService;
import com.inyaa.web.posts.vo.PostCommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author: yuxh
 * @date: 2021/3/16 0:45
 */
@RestController
@RequestMapping("/post/comment")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @GetMapping("/list")
    public BaseResult<Page<PostCommentVO>> list(PostComment req) {
        req.setPage(req.getPage() - 1);
        return postCommentService.list(req);
    }

    @PostMapping("/save")
    public BaseResult<String> save(@RequestBody PostComment req) {
        postCommentService.save(req);
        return BaseResult.success();
    }
}
