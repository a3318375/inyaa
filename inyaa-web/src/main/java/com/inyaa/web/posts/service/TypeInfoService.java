package com.inyaa.web.posts.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.posts.bean.TypeInfo;
import com.inyaa.web.posts.dao.TypeInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/6 0:13
 */
@Service
@RequiredArgsConstructor
public class TypeInfoService {

    private final TypeInfoDao typeInfoDao;

    public void delete(Integer id) {
        typeInfoDao.deleteById(id);
    }

    public BaseResult<List<TypeInfo>> list() {
        return BaseResult.success(typeInfoDao.findAll());
    }

    public void save(TypeInfo typeInfo) {
        typeInfoDao.save(typeInfo);
    }
}
