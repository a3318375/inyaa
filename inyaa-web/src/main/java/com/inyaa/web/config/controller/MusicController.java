package com.inyaa.web.config.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.config.bean.Music;
import com.inyaa.web.config.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/13 2:53
 */
@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/list")
    public BaseResult<List<Music>> getSysSiteConfigMap() {
        List<Music> resp = musicService.getPlayList();
        return BaseResult.success(resp);
    }

}
