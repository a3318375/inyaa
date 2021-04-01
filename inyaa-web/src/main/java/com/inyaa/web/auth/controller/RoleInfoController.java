package com.inyaa.web.auth.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.RoleInfo;
import com.inyaa.web.auth.service.RoleInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/31 23:12
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleInfoController {

    private final RoleInfoService roleInfoService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody RoleInfo roleInfo) {
        roleInfoService.save(roleInfo);
        return BaseResult.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody RoleInfo roleInfo) {
        roleInfoService.delete(roleInfo);
        return BaseResult.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResult<List<RoleInfo>> findDeptTree() {
        List<RoleInfo> list =  roleInfoService.list();
        return BaseResult.success(list);
    }
}
