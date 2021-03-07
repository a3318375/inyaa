package com.inyaa.web.posts.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.dto.PostInfoDto;
import com.inyaa.web.posts.service.PostInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/get/{id}")
    public BaseResult<PostInfo> get(@PathVariable(value = "id") Integer id) {
        return postInfoService.get(id);
    }

    @GetMapping("/list")
    public BaseResult<Page<PostInfo>> list(PostInfoDto req) {
        return postInfoService.list(req);
    }

    @PostMapping("/delete/{id}")
    public BaseResult<String> delete(@PathVariable(value = "id") Integer id) {
        postInfoService.delete(id);
        return BaseResult.success();
    }
}
