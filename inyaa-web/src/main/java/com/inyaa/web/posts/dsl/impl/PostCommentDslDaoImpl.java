package com.inyaa.web.posts.dsl.impl;

import com.inyaa.web.posts.bean.PostComment;
import com.inyaa.web.posts.bean.QPostComment;
import com.inyaa.web.posts.dsl.PostCommentDslDao;
import com.inyaa.web.posts.vo.PostCommentVO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCommentDslDaoImpl implements PostCommentDslDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostCommentVO> findPostCommentListPage(PostComment req) {
        Pageable page = PageRequest.of(req.getPage(), req.getSize());
        QPostComment qBean = QPostComment.postComment;
        JPAQuery<PostCommentVO> jpaQuery = jpaQueryFactory
                .select(Projections.bean(PostCommentVO.class
                        , qBean.id, qBean.userId, qBean.toUserId,
                        qBean.type, qBean.content, qBean.postId,
                        qBean.createTime)).from(qBean);
        jpaQuery.where(qBean.postId.eq(req.getPostId()));
        jpaQuery.where(qBean.type.eq(req.getType()));
        jpaQuery.orderBy(qBean.createTime.desc());

        jpaQuery.offset(page.getOffset()).limit(page.getPageSize());
        QueryResults<PostCommentVO> queryResults = jpaQuery.fetchResults();
        return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
    }

    @Override
    public List<PostCommentVO> findPostCommentList(PostComment req) {
        QPostComment qBean = QPostComment.postComment;
        JPAQuery<PostCommentVO> jpaQuery = jpaQueryFactory
                .select(Projections.bean(PostCommentVO.class
                        , qBean.id, qBean.userId, qBean.toUserId,
                        qBean.type, qBean.content, qBean.postId,
                        qBean.createTime)).from(qBean);
        jpaQuery.where(qBean.postId.eq(req.getPostId()));
        jpaQuery.where(qBean.type.eq(req.getType()));
        jpaQuery.orderBy(qBean.createTime.desc());
        return jpaQuery.fetch();
    }
}
