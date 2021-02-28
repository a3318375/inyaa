//package com.inyaa.web.posts.service;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.inyaa.web.auth.dao.AuthUserDao;
//import com.inyaa.web.auth.domain.po.AuthUser;
//import com.inyaa.web.posts.dao.PostsCommentsDao;
//import com.inyaa.web.posts.domain.po.PostsComments;
//import com.inyaa.web.posts.domain.vo.PostsCommentsVO;
//import com.yuxh.inyaa.web.common.base.domain.Result;
//import com.yuxh.inyaa.web.common.base.domain.vo.UserSessionVO;
//import com.yuxh.inyaa.web.common.base.service.impl.BaseServiceImpl;
//import com.yuxh.inyaa.web.common.constant.Constants;
//import com.yuxh.inyaa.web.common.enums.ErrorEnum;
//import com.yuxh.inyaa.web.common.util.ExceptionUtil;
//import com.yuxh.inyaa.web.common.util.PageUtil;
//import com.yuxh.inyaa.web.common.util.SessionUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
///**
// * <p>
// * 评论表 服务实现类
// * </p>
// * @author byteblogs
// * @since 2019-09-03
// */
//@Service
//public class PostsCommentsService extends BaseServiceImpl<PostsCommentsDao, PostsComments> implements PostsCommentsService {
//
//    @Autowired
//    private PostsCommentsDao postsCommentsDao;
//
//    @Autowired
//    private PostsDao postsDao;
//
//    @Autowired
//    private AuthUserDao authUserDao;
//
//    @Override
//    public Result savePostsComments(PostsCommentsVO postsCommentsVO) {
//        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
//        PostsComments postsComments = new PostsComments();
//        postsComments.setAuthorId(userSessionInfo.getId());
//        postsComments.setContent(postsCommentsVO.getContent());
//        postsComments.setPostsId(postsCommentsVO.getPostsId());
//        postsComments.setCreateTime(LocalDateTime.now());
//
//        String treePath;
//        if (postsCommentsVO.getParentId() == null) {
//            this.postsCommentsDao.insert(postsComments);
//            treePath = postsComments.getId() + Constants.TREE_PATH;
//        } else {
//            PostsComments parentPostsComments = this.postsCommentsDao.selectById(postsCommentsVO.getParentId());
//            if (parentPostsComments == null) {
//                ExceptionUtil.rollback(ErrorEnum.DATA_NO_EXIST);
//            }
//
//            postsComments.setParentId(postsCommentsVO.getParentId());
//            this.postsCommentsDao.insert(postsComments);
//
//            treePath = parentPostsComments.getTreePath() + postsComments.getId() + Constants.TREE_PATH;
//        }
//
//        this.postsCommentsDao.updateById(postsComments.setTreePath(treePath));
//
//        this.postsDao.incrementComments( postsCommentsVO.getPostsId());
//
//        return Result.createWithSuccessMessage();
//    }
//
//
//    @Override
//    public Result replyComments(PostsCommentsVO postsCommentsVO) {
//        AuthUser authUser=authUserDao.selectAdmin();
//        PostsComments postsComments=postsCommentsDao.selectById(postsCommentsVO.getParentId())
//                .setParentId(postsCommentsVO.getParentId())
//                .setContent(postsCommentsVO.getContent())
//                .setAuthorId(authUser.getId())
//                .setCreateTime(LocalDateTime.now());
//        this.postsCommentsDao.insert(postsComments);
//        String treePath = postsComments.getTreePath() + postsComments.getId() + Constants.TREE_PATH;
//        this.postsCommentsDao.updateById(postsComments.setTreePath(treePath));
//        this.postsDao.incrementComments( postsCommentsVO.getPostsId());
//        return Result.createWithSuccessMessage();
//    }
//
//    @Override
//    public Result getPostsCommentsByPostsIdList(PostsCommentsVO postsCommentsVO) {
//
//        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(postsCommentsVO)).orElse(PageUtil.initPage());
//        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsByPostsIdList(page, postsCommentsVO.getPostsId());
//
//        return Result.createWithPaging(postsCommentsVOLis, PageUtil.initPageInfo(page));
//    }
//
//    @Override
//    public Result getPostsCommentsList(PostsCommentsVO postsCommentsVO) {
//        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(postsCommentsVO)).orElse(PageUtil.initPage());
//        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsList(page, postsCommentsVO);
//        return Result.createWithPaging(postsCommentsVOLis, PageUtil.initPageInfo(page));
//    }
//
//    @Override
//    public Result deletePostsComments(Long id) {
//        this.postsCommentsDao.deleteById(id);
//        return Result.createWithSuccessMessage();
//    }
//
//    @Override
//    public Result getPostsComment(Long id) {
//        ExceptionUtil.isRollback(id==null,ErrorEnum.PARAM_ERROR);
//        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsList(new PostsCommentsVO().setId(id));
//        if (postsCommentsVOLis!=null && postsCommentsVOLis.size()>0){
//            return Result.createWithModel(postsCommentsVOLis.get(0));
//        }
//        return Result.createWithError();
//    }
//}
