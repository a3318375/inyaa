//package com.inyaa.web.posts.controller;
//
//import com.inyaa.web.posts.domain.validator.InsertPostsComments;
//import com.inyaa.web.posts.domain.validator.QueryPostsComments;
//import com.inyaa.web.posts.domain.vo.PostsCommentsVO;
//import com.yuxh.inyaa.web.common.annotation.LoginRequired;
//import com.yuxh.inyaa.web.common.base.domain.Result;
//import com.yuxh.inyaa.web.system.enums.RoleEnum;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author byteblogs
// * @since 2019-09-03
// */
//@RestController
//@RequestMapping("/comments")
//public class PostsCommentsController {
//
//    @Autowired
//    private PostsCommentsService postsCommentsService;
//
//    @LoginRequired(role = RoleEnum.USER)
//    @PostMapping("/comments/add")
//    public Result savePostsComments(@Validated({InsertPostsComments.class}) @RequestBody PostsCommentsVO postsCommentsVO) {
//        return this.postsCommentsService.savePostsComments(postsCommentsVO);
//    }
//
//    @LoginRequired(role = RoleEnum.USER)
//    @PostMapping("/admin/reply")
//    public Result replyComments(@RequestBody PostsCommentsVO postsCommentsVO) {
//        return this.postsCommentsService.replyComments(postsCommentsVO);
//    }
//
//    @LoginRequired
//    @DeleteMapping("/comments/{id}")
//    public Result deletePostsComments(@PathVariable(value = "id") Long id) {
//        return this.postsCommentsService.deletePostsComments(id);
//    }
//
//    @LoginRequired
//    @GetMapping("/comments/{id}")
//    public Result getPostsComment(@PathVariable(value = "id") Long id) {
//        return this.postsCommentsService.getPostsComment(id);
//    }
//
//    @GetMapping("/comments-posts/list")
//    public Result getPostsCommentsByPostsIdList(@Validated({QueryPostsComments.class}) PostsCommentsVO postsCommentsVO) {
//        return this.postsCommentsService.getPostsCommentsByPostsIdList(postsCommentsVO);
//    }
//
//    @LoginRequired
//    @GetMapping("/comments/get")
//    public Result getPostsCommentsList(PostsCommentsVO postsCommentsVO) {
//        return this.postsCommentsService.getPostsCommentsList(postsCommentsVO);
//    }
//}
