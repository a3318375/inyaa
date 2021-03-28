package com.inyaa.web.auth.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.SysDept;
import com.inyaa.web.auth.service.SysDeptService;
import com.inyaa.web.auth.vo.SysDepotVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/27 19:04
 */
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody SysDept sysDept) {
        sysDeptService.save(sysDept);
        return BaseResult.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody SysDept sysDept) {
        sysDeptService.delete(sysDept);
        return BaseResult.success();
    }

    @RequestMapping(value = "/findDeptTree", method = RequestMethod.GET)
    public BaseResult<List<SysDepotVo>> findDeptTree() {
        List<SysDepotVo> list =  sysDeptService.findDeptTree();
        return BaseResult.success(list);
    }
}
