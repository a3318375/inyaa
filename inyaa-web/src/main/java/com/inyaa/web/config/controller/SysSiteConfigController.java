package com.inyaa.web.config.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.config.service.SysSiteConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: yuxh
 * @date: 2021/3/13 0:58
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class SysSiteConfigController {

    private final SysSiteConfigService sysSiteConfigService;

    @GetMapping("/get")
    public BaseResult<Map<String, String>> getSysSiteConfigMap() {
        Map<String, String> resp = sysSiteConfigService.getConfigMap();
        return BaseResult.success(resp);
    }
}
