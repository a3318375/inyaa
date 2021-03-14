package com.inyaa.web.config.service;

import com.inyaa.web.config.bean.SysOssConfig;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

    String upload(MultipartFile file, SysOssConfig config);

}
