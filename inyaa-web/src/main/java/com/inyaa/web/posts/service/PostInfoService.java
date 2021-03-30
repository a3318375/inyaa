package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.PostArticle;
import com.inyaa.web.posts.bean.PostInfo;
import com.inyaa.web.posts.bean.PostTag;
import com.inyaa.web.posts.dao.PostArticleDao;
import com.inyaa.web.posts.dao.PostInfoDao;
import com.inyaa.web.posts.dao.PostTagDao;
import com.inyaa.web.posts.dto.PostInfoDto;
import com.inyaa.web.posts.vo.PostsAdminVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        postArticleDao.deleteByPostId(info.getId());
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

    public BaseResult<Map<String, Map<String, List<PostInfo>>>> archive() {
        Sort sort = Sort.by("createTime").descending();
        List<PostInfo> list = postInfoDao.findAll(sort);
        Map<String, Map<String, List<PostInfo>>> resp = new LinkedHashMap<>();
        list.forEach(post -> {
            String year = post.getCreateTime().getYear() + "年";
            String month = post.getCreateTime().getMonthValue() + "月";
            Map<String, List<PostInfo>> monthMap;
            if (resp.containsKey(year)) {
                monthMap = resp.get(year);
                List<PostInfo> monthList;
                if (monthMap.containsKey(month)) {
                    monthList = monthMap.get(month);
                } else {
                    monthList = new ArrayList<>();
                }
                monthList.add(post);
                monthMap.put(month, monthList);
            } else {
                monthMap = new LinkedHashMap<>();
                List<PostInfo> monthList = new ArrayList<>();
                monthList.add(post);
                monthMap.put(month, monthList);
            }
            resp.put(year, monthMap);
        });
        return BaseResult.success(resp);
    }
}
