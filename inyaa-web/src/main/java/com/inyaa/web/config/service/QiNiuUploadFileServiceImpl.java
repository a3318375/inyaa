package com.inyaa.web.config.service;

import com.google.gson.Gson;
import com.inyaa.base.util.FileUtil;
import com.inyaa.web.config.bean.SysOssConfig;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Administrator
 */
@Service
public class QiNiuUploadFileServiceImpl implements UploadFileService {


    @Override
    public String upload(MultipartFile file, SysOssConfig config) {
        Configuration cfg = new Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(config.getAesKey(), config.getSecretKey());
        String upToken = auth.uploadToken(config.getBucket());
        try {
            Response response = uploadManager.put(file.getInputStream(), FileUtil.createSingleFileName(file.getOriginalFilename()), upToken, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return config.getUrl() + putRet.key;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
