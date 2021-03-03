package com.inyaa.web.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.service.MenuInfService;
import com.inyaa.web.auth.vo.MenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return BaseResult.success(menuInfService.findMenuList());
    }
}
