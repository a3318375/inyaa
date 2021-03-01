package com.inyaa.web.controller;


import com.inyaa.base.bean.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1></h1>
 * Created by hanqf on 2020/11/7 01:24.
 */

@RestController
public class RbacController {

    @GetMapping("/rbac")
    public BaseResult demo(){
        return BaseResult.success("rbac");
    }
}
