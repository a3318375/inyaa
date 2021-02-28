//package com.inyaa.web.posts.dao.impl;
//
//import com.inyaa.web.posts.bean.QPosts;
//import com.inyaa.web.posts.dao.BasePostsRepository;
//import com.inyaa.web.posts.vo.PostsVO;
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.QBean;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.core.types.dsl.StringTemplate;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author: yuxh
// * @date: 2021/2/27 14:41
// */
//@Repository
//public class PostsRepositoryImpl implements BasePostsRepository {
//
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    public List<PostsVO> findArchiveTotalGroupDateList(){
//        QPosts qPosts = QPosts.posts;
//        StringTemplate dateExpr = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-01 00:00:00')", qPosts.createTime);
//        QBean<PostsVO> qBean = Projections.bean(PostsVO.class, dateExpr, qPosts.count());
//        return jpaQueryFactory.select(qBean).from(qPosts).groupBy(dateExpr).fetch();
//    }
//
//    public List<PostsVO> findByArchiveDate(PostsVO postsVO){
//        QPosts qPosts = QPosts.posts;
//        StringTemplate req = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-01 00:00:00')", postsVO.getArchiveDate());
//        StringTemplate createTime = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-01 00:00:00')", qPosts.createTime);
//        QBean<PostsVO> qBean = Projections.bean(PostsVO.class, qPosts);
//        JPAQuery<PostsVO> jpaQuery = jpaQueryFactory.select(qBean).from(qPosts);
//        jpaQuery.where(req.eq(createTime)); //TODO
//        return jpaQuery.fetchResults().getResults();
//    }
//
//    public Page<PostsVO> findPostsPage(PostsVO postsVO){
//        Pageable page = PageRequest.of(postsVO.getPage() , postsVO.getSize());
//        QPosts qPosts = QPosts.posts;
//        QBean<PostsVO> qBean = Projections.bean(PostsVO.class, qPosts);
//        JPAQuery<PostsVO> jpaQuery = jpaQueryFactory.select(qBean).from(qPosts).leftJoin(qPosts).on(qPosts.id.eq(qPosts.id));
//        jpaQuery.where(qPosts.title.like(postsVO.getTitle())); //TODO
//        QueryResults<PostsVO> queryResults = jpaQuery.fetchResults();
//        return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
//    }
//
//}
