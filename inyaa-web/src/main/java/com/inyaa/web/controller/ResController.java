package com.inyaa.web.controller;

import com.inyaa.base.bean.BaseResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * <h1>获取资源服务器数据</h1>
 * Created by hanqf on 2020/11/7 22:47.
 */

@RestController
@RequestMapping("/res")
public class ResController {

    private RestTemplate restTemplate;

    public ResController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * 获取资源服务器的数据
    */
    @RequestMapping("/res1")
    public BaseResult getRes(){
        return restTemplate.getForObject("http://localhost:8082/res/res1", BaseResult.class);
    }

    @RequestMapping("/user")
    public BaseResult getUser(){
        return restTemplate.postForObject("http://localhost:8082/user",null, BaseResult.class);
    }

    @RequestMapping("/rbac")
    public BaseResult getRbac(){
        return restTemplate.getForObject("http://localhost:8082/rbac", BaseResult.class);
    }

    @RequestMapping("/userInfo")
    public Map<String, Object> getuserInfo(){
        return restTemplate.getForObject("http://localhost:8082/userInfo", Map.class);
    }


}
