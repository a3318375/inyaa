//package com.inyaa.web.posts.controller;
//
//import com.inyaa.web.posts.domain.vo.PostsVO;
//import com.inyaa.web.posts.service.PostsService;
//import com.yuxh.inyaa.web.common.base.domain.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/archive")
//public class ArchiveController {
//
//    @Autowired
//    private PostsService postsService;
//
//    @GetMapping("/archive/list")
//    public Result<PostsVO> getArchiveTotalByDateList(PostsVO postsVO) {
//        return postsService.getArchiveTotalByDateList(postsVO);
//    }
//
//    @GetMapping("/year/list")
//    public Result<PostsVO> getArchiveGroupYearList(PostsVO postsVO) {
//        return postsService.getArchiveGroupYearList(postsVO);
//    }
//}
