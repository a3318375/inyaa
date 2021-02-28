//package com.inyaa.web.posts.controller;
//
//import com.inyaa.base.bean.BaseResult;
//import com.inyaa.web.posts.service.PostsService;
//import com.inyaa.web.posts.vo.PostsVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
///**
// * @author byteblogs@aliyun.com
// * @since 2019-08-28
// */
//@RestController
//@RequestMapping("/posts")
//public class PostsController {
//
//    @Autowired
//    private PostsService postsService;
//
//    @GetMapping("/list")
//    public BaseResult<Page<PostsVO>> getPostsList(PostsVO postsVO) {
//        return postsService.getPostsList(postsVO);
//    }
//
//    @GetMapping("/weight/list")
//    public BaseResult<Page<PostsVO>> getWeightList(PostsVO postsVO) {
//        postsVO.setIsWeight(1);
//        return postsService.getPostsList(postsVO);
//    }
//
//    @GetMapping("/archive/list")
//    public BaseResult<List<PostsVO>> getArchiveTotalByDateList() {
//        return postsService.getArchiveTotalByDateList();
//    }
//
//    @GetMapping("/hot/list")
//    public BaseResult getHotPostsList(PostsVO postsVO) {
//        return postsService.getHotPostsList(postsVO);
//    }
//
//    @LoginRequired
//    @PostMapping("/posts/add")
//    public Result savePosts(@Validated({InsertPosts.class}) @RequestBody PostsVO postsVO, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return postsService.savePosts(postsVO);
//    }
//
//    @PostMapping("/byte-blogs/publish")
//    public Result publishByteBlogs(@Validated({Update.class}) @RequestBody PostsVO postsVO, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return postsService.publishByteBlogs(postsVO);
//    }
//
//    @GetMapping("/posts/{id}")
//    public Result getPosts(@PathVariable Long id) {
//        return this.postsService.getPosts(id);
//    }
//
//    @DeleteMapping("/posts/{id}")
//    public Result deletePosts(@PathVariable Long id) {
//        return this.postsService.deletePosts(id);
//    }
//
//    @PutMapping("/posts/update")
//    public Result updatePosts(@Validated({UpdatePosts.class}) @RequestBody PostsVO postsVO, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return this.postsService.updatePosts(postsVO);
//    }
//
//    @PutMapping("/status/update")
//    public Result updatePostsStatus(@Validated({UpdateStatus.class}) @RequestBody PostsVO postsVO, BindingResult result) {
//        ThrowableUtils.checkParamArgument(result);
//        return this.postsService.updatePostsStatus(postsVO);
//    }
//
//}
