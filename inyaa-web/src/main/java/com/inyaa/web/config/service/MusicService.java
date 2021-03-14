package com.inyaa.web.config.service;

import com.inyaa.web.config.bean.Music;
import com.inyaa.web.config.dao.SysSiteConfigDao;
import com.inyaa.web.config.util.MusicUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/13 2:55
 */
@Service
@RequiredArgsConstructor
public class MusicService {

    private final SysSiteConfigDao sysSiteConfigDao;

    public List<Music> getPlayList() {
        String value = sysSiteConfigDao.getMusicConfig();
        return MusicUtil.getPlayList(value);
    }
}
