package com.inyaa.web.posts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yuxh
 * @date: 2021/2/28 15:24
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/test")
    public String savePostsComments() {
        return "123123";
    }
}
