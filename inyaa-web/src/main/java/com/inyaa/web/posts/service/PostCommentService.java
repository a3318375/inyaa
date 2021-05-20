package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.service.AuthUserService;
import com.inyaa.web.posts.bean.PostComment;
import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.dao.PostCommentDao;
import com.inyaa.web.posts.dao.PostInfoDao;
import com.inyaa.web.posts.vo.PostCommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: yuxh
 * @date: 2021/3/16 0:47
 */
@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostInfoDao postInfoDao;
    private final PostCommentDao postCommentDao;
    private final AuthUserService authUserService;

    public BaseResult<Page<PostCommentVO>> list(PostComment req) {
        Page<PostCommentVO> list = postCommentDao.findPostCommentListPage(req);
        list.getContent().forEach(this::setUserInfo);
        return BaseResult.success(list);
    }

    private void setUserInfo(PostCommentVO postComment) {
        if (postComment.getUserId() != null) {
            UserInfo userInfo = authUserService.getUserById(postComment.getUserId());
            postComment.setName(userInfo.getName());
            postComment.setAvatar(userInfo.getAvatar());
        }
        if (postComment.getToUserId() != null) {
            UserInfo toUserInfo = authUserService.getUserById(postComment.getToUserId());
            postComment.setToUserName(toUserInfo.getName());
            postComment.setToUserAvatar(toUserInfo.getAvatar());
        }
    }

    public void save(PostComment req) {
        req.setCreateTime(LocalDateTime.now());
        postCommentDao.save(req);

        PostInfo info = postInfoDao.getOne(req.getPostId());
        info.setComments(info.getComments() + 1);
        postInfoDao.save(info);
    }
}
