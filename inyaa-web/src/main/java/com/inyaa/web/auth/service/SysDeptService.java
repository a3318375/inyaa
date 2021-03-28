package com.inyaa.web.auth.service;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.SysDept;
import com.inyaa.web.auth.dao.SysDeptDao;
import com.inyaa.web.auth.vo.SysDepotVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/27 18:52
 */
@Service
@RequiredArgsConstructor
public class SysDeptService {

    private final SysDeptDao sysDeptDao;

    public void save(SysDept sysDepot) {
        sysDeptDao.save(sysDepot);

    }

    public void delete(SysDept sysDepot) {
        sysDeptDao.deleteById(sysDepot.getId());
    }

    public BaseResult<List<SysDept>> findDeptList() {
        Sort sort = Sort.by("sort").ascending();
        List<SysDept> resp = sysDeptDao.findAll(sort);
        return BaseResult.success(resp);
    }

    public List<SysDept> findDeptTree(Integer pid) {
        SysDept dept = new SysDept();
        dept.setEnable(true);
        dept.setParentId(pid);
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<SysDept> ex = Example.of(dept, matcher);
        Sort sort = Sort.by("sort").ascending();
        return sysDeptDao.findAll(ex, sort);
    }

    private List<SysDepotVo> findDeptTree(List<SysDept> list) {
        List<SysDepotVo> depotVos = new ArrayList<>();
        list.forEach(dept -> {
            SysDepotVo vo = new SysDepotVo();
            BeanUtils.copyProperties(dept, vo);
            List<SysDept> chindres = findDeptTree(vo.getId());
            if (chindres.size() > 0) {
                vo.setChildren(findDeptTree(chindres));
            }
            depotVos.add(vo);
        });
        return depotVos;
    }

    public List<SysDepotVo> findDeptTree() {
        return findDeptTree(findDeptTree(0));
    }

}
