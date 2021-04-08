package com.inyaa.web.config.service;

import com.inyaa.web.config.bean.SysFile;
import com.inyaa.web.config.dao.SysFileDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/29 23:20
 */
@Service
@RequiredArgsConstructor
public class SysFileService {

    private final SysFileDao sysFileDao;

    public void save(String url, Integer type) {
        SysFile sysFile = new SysFile();
        sysFile.setUrl(url);
        sysFile.setType(type == null ? 0 : type);
        sysFile.setCreateTime(LocalDateTime.now());
        sysFileDao.save(sysFile);
    }

    public List<SysFile> findAll() {
        return sysFileDao.findAll();
    }

    public SysFile getRandImg(int type) {
        return sysFileDao.getRandImg(type);
    }
}
