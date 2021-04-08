package com.inyaa.web.config.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.base.util.FileUtil;
import com.inyaa.web.config.bean.SysFile;
import com.inyaa.web.config.service.SysFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author: yuxh
 * @date: 2021/3/29 22:55
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final SysFileService sysFileService;

    @PostMapping("/upload")
    public BaseResult<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String saveName = "/cover/" + FileUtil.getUUID32() + filename.substring(filename.indexOf("."));
        Response resp = FileUtil.uploadByUpai(file, saveName);
        if (resp != null && resp.isSuccessful()) {
            assert resp.body() != null;
            sysFileService.save("https://media.inyaa.cn" + saveName, 0);
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }
    }

    @GetMapping("/list")
    public BaseResult<List<SysFile>> upload() {
        List<SysFile> list = sysFileService.findAll();
        return BaseResult.success(list);
    }

    @GetMapping("/get")
    public BaseResult<SysFile> get(int type) {
        SysFile file = sysFileService.getRandImg(type);
        return BaseResult.success(file);
    }
}
