package com.inyaa.web.posts.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.dto.PostInfoDto;
import com.inyaa.web.posts.service.PostInfoService;
import com.inyaa.web.posts.vo.PostArchiveVo;
import com.inyaa.web.posts.vo.PostsAdminVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author byteblogs@aliyun.com
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostsController {

    private final PostInfoService postInfoService;

    @PostMapping("/save")
    public BaseResult<String> save(@RequestBody PostInfoDto dto) {
        postInfoService.save(dto);
        return BaseResult.success();
    }

    @GetMapping("/get")
    public BaseResult<PostsAdminVO> get(Integer id) {
        return postInfoService.get(id);
    }

    @GetMapping("/list")
    public BaseResult<Page<PostInfo>> list(PostInfoDto req) {
        req.setPage(req.getPage() - 1);
        return postInfoService.list(req);
    }

    @GetMapping("/weight/list")
    public BaseResult<Page<PostInfo>> weight(PostInfoDto req) {
        req.setWeight(1);
        req.setPage(req.getPage() - 1);
        return postInfoService.list(req);
    }

    @GetMapping("/archive/list")
    public BaseResult<List<PostArchiveVo>> archive() {
        return postInfoService.archive();
    }

    @PostMapping("/delete")
    public BaseResult<String> delete(@RequestBody Integer id) {
        postInfoService.delete(id);
        return BaseResult.success();
    }
}
