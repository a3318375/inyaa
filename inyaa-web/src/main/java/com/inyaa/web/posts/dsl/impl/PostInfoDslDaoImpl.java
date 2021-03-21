package com.inyaa.web.posts.dsl.impl;

import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.bean.QPostInfo;
import com.inyaa.web.posts.bean.QPostTag;
import com.inyaa.web.posts.dsl.PostInfoDslDao;
import com.inyaa.web.posts.dto.PostInfoDto;
import com.inyaa.web.posts.vo.PostArchiveVo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/2/27 14:41
 */
@Repository
@RequiredArgsConstructor
public class PostInfoDslDaoImpl implements PostInfoDslDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostInfo> findPostListPage(PostInfoDto req) {
        Pageable page = PageRequest.of(req.getPage(), req.getSize());
        QPostInfo qBean = QPostInfo.postInfo;
        JPAQuery<PostInfo> jpaQuery = jpaQueryFactory.select(qBean).from(qBean);
        if (req.getTagId() != null) {
            QPostTag qPostTag = QPostTag.postTag;
            jpaQuery.leftJoin(qPostTag).on(qPostTag.postId.eq(qBean.id));
            jpaQuery.where(qPostTag.tagId.eq(req.getTagId()));
        }
        if (req.getCreateTime() != null) {
            StringTemplate eqTime = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", req.getCreateTime());
            String queryDate = req.getCreateTime().format(DateTimeFormatter.ISO_DATE);
            jpaQuery.where(eqTime.eq(queryDate));
        }
        if (StringUtils.isNoneBlank(req.getTitle())) {
            jpaQuery.where(qBean.title.like(req.getTitle()));
        }
        if (req.getStatus() != null) {
            jpaQuery.where(qBean.status.eq(req.getStatus()));
        }
        if (req.getWeight() != null) {
            jpaQuery.where(qBean.weight.eq(req.getWeight()));
        }
        if (req.getTypeId() != null) {
            jpaQuery.where(qBean.typeId.eq(req.getTypeId()));
        }
        jpaQuery.orderBy(qBean.createTime.desc());
        jpaQuery.offset(page.getOffset()).limit(page.getPageSize());
        QueryResults<PostInfo> queryResults = jpaQuery.fetchResults();
        return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
    }

    @Override
    public List<PostArchiveVo> findArchiveList() {
        QPostInfo qBean = QPostInfo.postInfo;
        StringTemplate dateFmt = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m')", qBean.createTime);
        JPAQuery<PostArchiveVo> jpaQuery = jpaQueryFactory
                .select(Projections.bean(PostArchiveVo.class, dateFmt.as("archiveDate")))
                .from(qBean)
                .groupBy(dateFmt)
                .orderBy(dateFmt.desc());
        return jpaQuery.fetch();
    }

    @Override
    public List<PostInfo> findByArchiveDate(String archiveDate) {
        QPostInfo qBean = QPostInfo.postInfo;
        StringTemplate dateFmt = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m')", qBean.createTime);
        JPAQuery<PostInfo> jpaQuery = jpaQueryFactory
                .select(qBean)
                .from(qBean)
                .where(dateFmt.eq(archiveDate));
        return jpaQuery.fetch();
    }
}
