package com.inyaa.web.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.SysMenu;
import com.inyaa.web.auth.service.MenuInfService;
import com.inyaa.web.auth.vo.MenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/4 0:13
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuInfService menuInfService;

    @GetMapping("/list")
    public BaseResult<List<MenuVo>> list(){
        return BaseResult.success(menuInfService.findMenuList(1));
    }

    @GetMapping("/admin/list")
    public BaseResult<List<MenuVo>> adminList(){
        return BaseResult.success(menuInfService.findMenuList(0));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody SysMenu sysMenu) {
        menuInfService.save(sysMenu);
        return BaseResult.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody SysMenu sysMenu) {
        menuInfService.delete(sysMenu);
        return BaseResult.success();
    }
}
