package com.inyaa.web.posts.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.TypeInfo;
import com.inyaa.web.posts.service.TypeInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/6 0:11
 */
@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
public class TypeController {

    private final TypeInfoService typeInfoService;

    @PostMapping("/save")
    public BaseResult<String> save(@RequestBody TypeInfo typeInfo) {
        typeInfoService.save(typeInfo);
        return BaseResult.success();
    }

    @GetMapping("/list")
    public BaseResult<List<TypeInfo>> list() {
        return typeInfoService.list();
    }

    @PostMapping("/delete")
    public BaseResult<String> delete(@RequestBody TypeInfo typeInfo) {
        typeInfoService.delete(typeInfo.getId());
        return BaseResult.success();
    }
}
