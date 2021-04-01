package com.inyaa.web.auth.service;

import com.inyaa.web.auth.bean.RoleInfo;
import com.inyaa.web.auth.dao.RoleInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/31 23:17
 */
@Service
@RequiredArgsConstructor
public class RoleInfoService {

    private final RoleInfoDao roleInfoDao;

    public void save(RoleInfo roleInfo) {
        roleInfoDao.save(roleInfo);
    }

    public void delete(RoleInfo roleInfo) {
        roleInfoDao.deleteById(roleInfo.getId());
    }

    public List<RoleInfo> list() {
        return roleInfoDao.findAll();
    }
}
