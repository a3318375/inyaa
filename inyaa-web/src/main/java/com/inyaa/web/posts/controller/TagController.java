package com.inyaa.web.posts.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.TagInfo;
import com.inyaa.web.posts.service.TagInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/6 0:12
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagInfoService tagInfoService;

    @PostMapping("/save")
    public BaseResult<String> save(@RequestBody TagInfo tagInfo) {
        tagInfoService.save(tagInfo);
        return BaseResult.success();
    }

    @GetMapping("/list")
    public BaseResult<List<TagInfo>> list() {
        return tagInfoService.list();
    }

    @PostMapping("/delete")
    public BaseResult<String> delete(@RequestBody TagInfo tagInfo) {
        tagInfoService.delete(tagInfo.getId());
        return BaseResult.success();
    }
}
