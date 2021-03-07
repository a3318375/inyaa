package com.inyaa.web.posts.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.TagInfo;
import com.inyaa.web.posts.service.TagInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/save")
    public BaseResult<String> save(TagInfo tagInfo) {
        tagInfoService.save(tagInfo);
        return BaseResult.success();
    }

    @GetMapping("/list")
    public BaseResult<List<TagInfo>> list() {
        return tagInfoService.list();
    }

    @GetMapping("/delete/{id}")
    public BaseResult<String> delete(@PathVariable(value = "id") Integer id) {
        tagInfoService.delete(id);
        return BaseResult.success();
    }
}
