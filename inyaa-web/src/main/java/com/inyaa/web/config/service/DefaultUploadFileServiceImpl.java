package com.inyaa.web.config.service;

import com.inyaa.base.enums.Constants;
import com.inyaa.base.util.FileUtil;
import com.inyaa.web.config.bean.SysOssConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DefaultUploadFileServiceImpl implements UploadFileService {

    @Override
    public String upload(MultipartFile file, SysOssConfig config) {
        String filePath = FileUtil.getDefaultPath(config.getPath());
        String fileName = FileUtil.createSingleFileName(file.getOriginalFilename());
        try {
            File destFile = new File(filePath);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config.getUrl() + Constants.FILE_URL + fileName;
    }
}
