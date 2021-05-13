package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.PostTag;
import com.inyaa.web.posts.bean.TagInfo;
import com.inyaa.web.posts.dao.PostTagDao;
import com.inyaa.web.posts.dao.TagInfoDao;
import com.inyaa.web.posts.vo.TagVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/6 0:22
 */
@Service
@RequiredArgsConstructor
public class TagInfoService {

    private final TagInfoDao tagInfoDao;
    private final PostTagDao postTagDao;

    public void delete(Integer id) {
        tagInfoDao.deleteById(id);
    }

    public BaseResult<List<TagInfo>> list() {
        return BaseResult.success(tagInfoDao.findAll());
    }

    public void save(TagInfo tagInfo) {
        tagInfo.setCreateTime(LocalDateTime.now());
        tagInfoDao.save(tagInfo);
    }

    public BaseResult<List<TagVo>> articleList() {
        List<TagVo> respList = new ArrayList<>();
        List<TagInfo> list = tagInfoDao.findAll();
        list.forEach(tagInfo -> {
            TagVo vo = new TagVo();
            BeanUtils.copyProperties(tagInfo, vo);

            PostTag req = new PostTag();
            req.setTagId(tagInfo.getId());
            ExampleMatcher matcher = ExampleMatcher.matching();
            Example<PostTag> ex = Example.of(req, matcher);
            long count = postTagDao.count(ex);
            vo.setCount(count);

            respList.add(vo);
        });
        return BaseResult.success(respList);
    }
}
