package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.PostArticle;
import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.bean.PostTag;
import com.inyaa.web.posts.dao.PostArticleDao;
import com.inyaa.web.posts.dao.PostInfoDao;
import com.inyaa.web.posts.dao.PostTagDao;
import com.inyaa.web.posts.dto.PostInfoDto;
import com.inyaa.web.posts.vo.PostArchiveVo;
import com.inyaa.web.posts.vo.PostsAdminVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/5 21:32
 */
@Service
@RequiredArgsConstructor
public class PostInfoService {

    private final PostInfoDao postInfoDao;

    private final PostArticleDao postArticleDao;

    private final PostTagDao postTagDao;

    public void save(PostInfoDto dto) {
        PostInfo info = new PostInfo();
        BeanUtils.copyProperties(dto, info);
        postInfoDao.save(info);

        PostArticle pa = new PostArticle();
        pa.setPostId(info.getId());
        pa.setContext(dto.getContext());
        postArticleDao.save(pa);

        if (dto.getTagList() != null) {
            for (Integer tag : dto.getTagList()) {
                PostTag pt = new PostTag();
                pt.setPostId(info.getId());
                pt.setTagId(tag);
                postTagDao.save(pt);
            }
        }
    }

    public BaseResult<PostsAdminVO> get(int id) {
        PostInfo info = postInfoDao.getOne(id);

        PostsAdminVO vo = new PostsAdminVO();
        BeanUtils.copyProperties(info, vo);
        String context = postArticleDao.getContextByPostId(info.getId());
        vo.setContext(context);
        List<Integer> tagIdList = postTagDao.findTagIdByPostId(info.getId());
        vo.setTagList(tagIdList);
        return BaseResult.success(vo);
    }

    public BaseResult<Page<PostInfo>> list(PostInfoDto req) {
        Page<PostInfo> list = postInfoDao.findPostListPage(req);
        return BaseResult.success(list);
    }

    public void delete(int id) {
        postInfoDao.deleteById(id);
    }

    public BaseResult<List<PostArchiveVo>> archive() {
        List<PostArchiveVo> list = postInfoDao.findArchiveList();
        list.forEach(vo -> {
            List<PostInfo> infoList = postInfoDao.findByArchiveDate(vo.getArchiveDate());
            vo.setArchivePosts(infoList);
            vo.setArticleTotal(infoList.size());
        });
        return BaseResult.success(list);
    }
}
