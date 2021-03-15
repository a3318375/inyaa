package com.inyaa.web.config.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.config.bean.SysSocialConfig;
import com.inyaa.web.config.service.SysSocialConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/15 23:38
 */
@RestController
@RequestMapping("/social")
@RequiredArgsConstructor
public class SysSocialConfigController {

    private final SysSocialConfigService sysSocialConfigService;

    @GetMapping("/list")
    public BaseResult<List<SysSocialConfig>> list() {
        List<SysSocialConfig> list = sysSocialConfigService.list();
        return BaseResult.success(list);
    }
}
