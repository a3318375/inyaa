package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.TagInfo;
import com.inyaa.web.posts.dao.TagInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/6 0:22
 */
@Service
@RequiredArgsConstructor
public class TagInfoService {

    private final TagInfoDao tagInfoDao;

    public void delete(Integer id) {
        tagInfoDao.deleteById(id);
    }

    public BaseResult<List<TagInfo>> list() {
        return BaseResult.success(tagInfoDao.findAll());
    }

    public void save(TagInfo tagInfo) {
        tagInfoDao.save(tagInfo);
    }
}
