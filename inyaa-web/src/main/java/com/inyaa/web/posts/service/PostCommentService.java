package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.service.AuthUserService;
import com.inyaa.web.posts.bean.PostComment;
import com.inyaa.web.posts.dao.PostCommentDao;
import com.inyaa.web.posts.vo.PostCommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/16 0:47
 */
@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentDao postCommentDao;
    private final AuthUserService authUserService;

    public BaseResult<Page<PostCommentVO>> list(PostComment req) {
        req.setType(0);
        Page<PostCommentVO> list = postCommentDao.findPostCommentListPage(req);
        for (PostCommentVO postComment : list.getContent()) {
            setUserInfo(postComment);

            PostComment params = new PostComment();
            params.setPostId(req.getPostId());
            params.setType(1);
            List<PostCommentVO> childList = postCommentDao.findPostCommentList(params);
            childList.forEach(this::setUserInfo);
        }
        return BaseResult.success(list);
    }

    private void setUserInfo(PostCommentVO postComment) {
        if (postComment.getUserId() != null) {
            UserInfo userInfo = authUserService.getUserById(postComment.getUserId());
            postComment.setName(userInfo.getName());
            postComment.setAvatar(userInfo.getAvatar());
        }
        if (postComment.getUserId() != null) {
            UserInfo toUserInfo = authUserService.getUserById(postComment.getToUserId());
            postComment.setToUserName(toUserInfo.getName());
            postComment.setToUserAvatar(toUserInfo.getAvatar());
        }
    }

    public void save(PostComment req) {
        postCommentDao.save(req);
    }
}
