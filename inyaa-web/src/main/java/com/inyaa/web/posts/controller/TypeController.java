package com.inyaa.web.posts.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.TypeInfo;
import com.inyaa.web.posts.service.TypeInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/save")
    public BaseResult<String> save(TypeInfo typeInfo) {
        typeInfoService.save(typeInfo);
        return BaseResult.success();
    }

    @GetMapping("/list")
    public BaseResult<List<TypeInfo>> list() {
        return typeInfoService.list();
    }

    @GetMapping("/delete/{id}")
    public BaseResult<String> delete(@PathVariable(value = "id") Integer id) {
        typeInfoService.delete(id);
        return BaseResult.success();
    }
}
