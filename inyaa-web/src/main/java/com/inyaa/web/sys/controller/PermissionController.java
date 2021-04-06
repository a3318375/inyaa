package com.inyaa.web.sys.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.sys.bean.Permission;
import com.inyaa.web.sys.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/4/6 23:02
 */
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {

    private final PermissionService permissionService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody Permission permission) {
        permission.setCreateTime(LocalDateTime.now());
        permissionService.save(permission);
        return BaseResult.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody Permission permission) {
        permissionService.delete(permission);
        return BaseResult.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResult<List<Permission>> list() {
        List<Permission> list =  permissionService.findAll();
        return BaseResult.success(list);
    }
}
