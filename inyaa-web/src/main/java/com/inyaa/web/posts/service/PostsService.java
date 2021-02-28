//package com.inyaa.web.posts.service;
//
//import cn.hutool.core.util.PageUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Constants;
//import com.inyaa.base.bean.BaseResult;
//import com.inyaa.base.util.Markdown2HtmlUtil;
//import com.inyaa.base.util.PreviewTextUtils;
//import com.inyaa.web.posts.bean.Posts;
//import com.inyaa.web.posts.bean.PostsAttribute;
//import com.inyaa.web.posts.bean.PostsTags;
//import com.inyaa.web.posts.bean.QPosts;
//import com.inyaa.web.posts.dao.PostsAttributeRepository;
//import com.inyaa.web.posts.dao.PostsRepository;
//import com.inyaa.web.posts.dao.PostsTagsRepository;
//import com.inyaa.web.posts.vo.PostsVO;
//import com.inyaa.web.tags.bean.Tags;
//import com.inyaa.web.tags.dao.TagsRepository;
//import com.inyaa.web.tags.vo.TagsVO;
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.QBean;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jdk.vm.ci.code.site.Site;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///**
// * @author byteblogs
// * @since 2019-08-28
// */
//@Service
//@Slf4j
//public class PostsService {
//
//    @Autowired
//    private PostsRepository postsRepository;
//    @Autowired
//    private PostsTagsRepository postsTagsRepository;
//    @Autowired
//    private JPAQueryFactory jpaQueryFactory;
//
//    @Autowired
//    private TagsRepository tagsRepository;
//    @Autowired
//    private PostsAttributeRepository postsAttributeRepository;
//    @Autowired
//    private PostsTagsRepository postsTagsRepository;
//
//    @Autowired
//    private AuthUserLogDao authUserLogDao;
//
//    public BaseResult<Posts> savePosts(PostsVO postsVO) {
//
//        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
//        String html = Markdown2HtmlUtil.html(postsVO.getContent());
//
//        Posts posts = new Posts();
//        LocalDateTime dateTime = Optional.ofNullable(postsVO.getCreateTime()).orElse(LocalDateTime.now());
//        posts.setTitle(postsVO.getTitle());
//        posts.setCreateTime(dateTime);
//        posts.setUpdateTime(dateTime);
//        posts.setThumbnail(postsVO.getThumbnail());
//        posts.setStatus(postsVO.getStatus());
//        posts.setSummary(PreviewTextUtils.getText(html, 126));
//        posts.setIsComment(postsVO.getIsComment());
//        posts.setAuthorId(userSessionInfo.getId());
//        posts.setCategoryId(postsVO.getCategoryId());
//        posts.setWeight(postsVO.getWeight());
//        postsRepository.save(posts);
//        postsVO.setId(posts.getId());
//
//        postsAttributeRepository.save(new PostsAttribute().setContent(postsVO.getContent()).setPostsId(posts.getId()));
//        List<TagsVO> tagsList = postsVO.getTagsList();
//        if (!CollectionUtils.isEmpty(tagsList)) {
//            tagsList.forEach(tagsVO -> {
//                if (tagsVO.getId() == null) {
//                    Tags tags = new Tags().setName(tagsVO.getName()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());// saveMenu
//                    tagsDao.insert(tags);
//                    tagsVO.setId(tags.getId());
//                }
//                postsTagsDao(new PostsTags().setPostsId(posts.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
//            });
//        }
//        return BaseResult.success();
//    }
//
//    public Result updatePosts(PostsVO postsVO) {
//
//        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
//        String html = Markdown2HtmlUtil.html(postsVO.getContent());
//
//        Posts posts1 = postsDao.selectOne(new LambdaQueryWrapper<Posts>().eq(Posts::getId, postsVO.getId()));
//        if (posts1 == null) {
//            ExceptionUtil.rollback(ErrorEnum.DATA_NO_EXIST);
//        }
//
//        posts1.setTitle(postsVO.getTitle()).setUpdateTime(LocalDateTime.now()).setThumbnail(postsVO.getThumbnail());
//        posts1.setStatus(postsVO.getStatus()).setSyncStatus(Constants.NO).setSummary(PreviewTextUtils.getText(html, 126)).setIsComment(postsVO.getIsComment())
//                .setAuthorId(userSessionInfo.getId()).setCategoryId(postsVO.getCategoryId()).setWeight(postsVO.getWeight());
//
//        postsDao.updateById(posts1);
//        Wrapper<PostsAttribute> wrapper = new LambdaUpdateWrapper<PostsAttribute>().eq(PostsAttribute::getPostsId, posts1.getId());
//        if (postsAttributeDao.selectCount(wrapper) > 0) {
//            postsAttributeDao.update(new PostsAttribute().setContent(postsVO.getContent()), wrapper);
//        } else {
//            postsAttributeDao.insert(new PostsAttribute().setContent(postsVO.getContent()).setPostsId(posts1.getId()));
//        }
//
//        List<TagsVO> tagsList = postsVO.getTagsList();
//
//        if (!CollectionUtils.isEmpty(tagsList)) {
//            List<PostsTags> originalList = postsTagsDao.selectList(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts1.getId()));
//            List<PostsTags> categoryTagsList = originalList.stream().filter(postsTags -> !postsVO.getTagsList().stream().map(BaseVO::getId).collect(Collectors.toList())
//                    .contains(postsTags.getTagsId())).collect(Collectors.toList());
//
//            if (!CollectionUtils.isEmpty(categoryTagsList)) {
//                categoryTagsList.forEach(categoryTags -> {
//                    postsTagsDao.deleteById(categoryTags.getId());
//                });
//            }
//
//            tagsList.forEach(tagsVO -> {
//                if (tagsVO.getId() == null) {
//                    // saveMenu
//                    Tags tags = new Tags().setName(tagsVO.getName()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());
//                    tagsDao.insert(tags);
//                    tagsVO.setId(tags.getId());
//                    postsTagsDao.insert(new PostsTags().setPostsId(posts1.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
//                } else {
//                    PostsTags postsTags = postsTagsDao.selectOne(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts1.getId()).eq(PostsTags::getTagsId, tagsVO.getId()));
//                    if (postsTags == null) {
//                        postsTagsDao.insert(new PostsTags().setPostsId(posts1.getId()).setTagsId(tagsVO.getId()).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now()));
//                    }
//                }
//            });
//        } else {
//
//            postsTagsDao.delete(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts1.getId()));
//        }
//
//        if (syncByteblogs(postsVO)) {
//            return Result.createWithSuccessMessage("同步到ByteBlogs成功");
//        }
//
//        return Result.createWithSuccessMessage();
//    }
//
//    public Result deletePosts(Long id) {
//
//        Posts posts = postsDao.selectById(id);
//        if (posts == null) {
//            ExceptionUtil.rollback(ErrorEnum.DATA_NO_EXIST);
//        }
//
//        postsDao.deleteById(id);
//        postsAttributeDao.delete(new LambdaUpdateWrapper<PostsAttribute>().eq(PostsAttribute::getPostsId, id));
//        postsTagsDao.delete(new LambdaUpdateWrapper<PostsTags>().eq(PostsTags::getPostsId, id));
//
//        return Result.createWithSuccessMessage();
//    }
//
//    public Result getPosts(Long id) {
//        Posts posts = postsDao.selectOneById(id);
//        if (posts == null) {
//            ExceptionUtil.rollback(ErrorEnum.DATA_NO_EXIST);
//        }
//
//        PostsVO postsVO = new PostsVO();
//        postsVO.setId(posts.getId())
//                .setCreateTime(posts.getCreateTime())
//                .setSummary(posts.getSummary())
//                .setTitle(posts.getTitle())
//                .setThumbnail(posts.getThumbnail())
//                .setIsComment(posts.getIsComment())
//                .setViews(posts.getViews())
//                .setComments(posts.getComments())
//                .setCategoryId(posts.getCategoryId())
//                .setWeight(posts.getWeight())
//                .setCategoryName(posts.getCategoryName());
//
//        PostsAttribute postsAttribute = postsAttributeDao.selectOne(new LambdaQueryWrapper<PostsAttribute>().eq(PostsAttribute::getPostsId, posts.getId()));
//        if (postsAttribute != null) {
//            postsVO.setContent(postsAttribute.getContent());
//        }
//        List<PostsTags> postsTagsList = postsTagsDao.selectList(new LambdaQueryWrapper<PostsTags>().eq(PostsTags::getPostsId, posts.getId()));
//        List<TagsVO> tagsVOList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(postsTagsList)) {
//            postsTagsList.forEach(postsTags -> {
//                Tags tags = tagsDao.selectById(postsTags.getTagsId());
//                tagsVOList.add(new TagsVO().setId(tags.getId()).setName(tags.getName()));
//            });
//        }
//
//        postsVO.setTagsList(tagsVOList);
//
//        postsDao.incrementView(posts.getId());
//        return Result.createWithModel(postsVO);
//    }
//
//    private Page<PostsVO> findPostsPage(PostsVO postsVO) {
//        Pageable page = PageRequest.of(postsVO.getPage(), postsVO.getSize());
//        QPosts qPosts = QPosts.posts;
//        QBean<PostsVO> qBean = Projections.bean(PostsVO.class, qPosts.id, qPosts.title);
//        JPAQuery<PostsVO> jpaQuery = jpaQueryFactory.select(qBean).from(qPosts).leftJoin(qPosts).on(qPosts.id.eq(qPosts.id));
//        jpaQuery.where(qPosts.title.like(postsVO.getTitle())); //TODO
//        QueryResults<PostsVO> queryResults = jpaQuery.fetchResults();
//        return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
//    }
//
//    public BaseResult<Page<PostsVO>> getPostsList(PostsVO postsVO) {
//        Page<PostsVO> postsVOList = findPostsPage(postsVO);
//        postsVOList.getContent().forEach(
//                vo -> {
//                    List<PostsTags> postsTagsList = postsTagsRepository.findByPostsId(vo.getId());
//                    List<TagsVO> tagsVOList = new ArrayList<>();
//                    postsTagsList.forEach(postsTags -> {
//                        Tags tags = tagsRepository.getOne(postsTags.getTagsId());
//                        TagsVO tagsVO = new TagsVO();
//                        tagsVO.setId(tags.getId());
//                        tagsVO.setName(tags.getName());
//                        tagsVOList.add(tagsVO);
//                    });
//                    vo.setTagsList(tagsVOList);
//                }
//        );
//        return BaseResult.success(postsVOList);
//    }
//
//
//    public BaseResult<List<PostsVO>> getArchiveTotalByDateList() {
//        List<PostsVO> postsVOList = postsRepository.findArchiveTotalGroupDateList();
//        postsVOList.forEach(obj -> {
//            // 查询每一个时间点中的文章
//            obj.setArchivePosts(postsRepository.findByArchiveDate(obj.getArchiveDate()));
//        });
//        return BaseResult.success(postsVOList);
//    }
//
//
//    public Result getArchiveGroupYearList(PostsVO postsVO) {
//        List<PostsVO> postsVOList = postsDao.selectArchiveGroupYearList();
//        return Result.createWithModels(postsVOList);
//    }
//
//
//    public Result updatePostsStatus(PostsVO postsVO) {
//        postsDao.updateById(new Posts().setId(postsVO.getId()).setStatus(postsVO.getStatus()).setUpdateTime(LocalDateTime.now()));
//        return Result.createWithSuccessMessage();
//    }
//
//
//    public Result publishByteBlogs(PostsVO postsVO) {
//        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
//        Posts posts = postsDao.selectById(postsVO.getId());
//        if (posts == null) {
//            ExceptionUtil.rollback(ErrorEnum.DATA_NO_EXIST);
//        }
//
//        PostsAttribute postsAttribute = postsAttributeDao.selectOne(new LambdaQueryWrapper<PostsAttribute>().eq(PostsAttribute::getPostsId, posts.getId()));
//        boolean syncStatus = syncByteblogs(new PostsVO().setTitle(posts.getTitle()).setIsPublishByteBlogs(Constants.YES).setContent(postsAttribute.getContent()));
//        log.debug("保存到ByteBlogs草稿箱 {}", syncStatus);
//        if (syncStatus) {
//            postsDao.update(new Posts().setSyncStatus(Constants.YES), new LambdaUpdateWrapper<Posts>().eq(Posts::getId, postsVO.getId()));
//            return Result.createWithSuccessMessage("同步到ByteBlogs成功，请点击" + Constants.BYTE_BLOGS_URL + "/editor/posts" + "进行编辑");
//        }
//
//        return Result.createWithErrorMessage(ErrorEnum.SYNC_POSTS_ERROR);
//    }
//
//
//    public Result getHotPostsList(PostsVO postsVO) {
//        postsVO = Optional.ofNullable(postsVO).orElse(new PostsVO());
//        Page page = Optional.of(PageUtil.checkAndInitPage(postsVO)).orElse(PageUtil.initPage());
//        if (StringUtils.isNotBlank(postsVO.getKeywords())) {
//            postsVO.setKeywords("%" + postsVO.getKeywords() + "%");
//        }
//        List<AuthUserLogVO> logVOList = authUserLogDao.selectListByCode(OperateEnum.GET_POSTS_DETAIL.getCode());
//        List<Long> ids = new ArrayList<>();
//        logVOList.forEach(obj -> {
//            JSONObject json = JSONObject.parseObject(obj.getParameter());
//            ids.add(json.getLong("id"));
//        });
//        postsDao.selectPage(page, new QueryWrapper<Posts>().in("id", ids));
//        return Result.createWithPaging(page.getRecords(), PageUtil.initPageInfo(page));
//    }
//
//    private void crawler(PostsVO postsVO) {
//        Class platformClass = PlatformEnum.getEnumTypeMap().get(postsVO.getPlatformType()).getPlatformClass();
//        Spider spider = OOSpider.create(Site.me(), platformClass).setDownloader(new HttpClientDownloader());
//        Object object = spider.get(postsVO.getSourceUri());
//
//        String join = "";
//        if (postsVO.getPlatformType().equals(PlatformEnum.JIAN_SHU.getType())) {
//            JianShuVO jianShuVO = (JianShuVO) object;
//            postsVO.setTitle(jianShuVO.getTitle());
//            join = String.join("", jianShuVO.getContent());
//        } else if (postsVO.getPlatformType().equals(PlatformEnum.JUE_JIN.getType())) {
//            JueJinVO jueJinVO = (JueJinVO) object;
//            postsVO.setTitle(jueJinVO.getTitle());
//            join = String.join("", jueJinVO.getContent());
//        } else if (postsVO.getPlatformType().equals(PlatformEnum.SEGMENT_FAULT.getType())) {
//            SegmentFaultVO segmentFaultVO = (SegmentFaultVO) object;
//            postsVO.setTitle(segmentFaultVO.getTitle());
//            join = String.join("", segmentFaultVO.getContent());
//        } else if (postsVO.getPlatformType().equals(PlatformEnum.CSDN.getType())) {
//            CSDNVO csdnVO = (CSDNVO) object;
//            postsVO.setTitle(csdnVO.getTitle());
//            join = String.join("", csdnVO.getContent());
//        } else if (postsVO.getPlatformType().equals(PlatformEnum.CN_BLOGS.getType())) {
//            CNBlogsVO cnBlogsVO = (CNBlogsVO) object;
//            postsVO.setTitle(cnBlogsVO.getTitle());
//            join = String.join("", cnBlogsVO.getContent());
//        } else {
//            ExceptionUtil.rollback(ErrorEnum.PARAM_ERROR);
//        }
//        String converted = new Remark().convertFragment(join);
//        postsVO.setContent(converted);
//    }
//}
