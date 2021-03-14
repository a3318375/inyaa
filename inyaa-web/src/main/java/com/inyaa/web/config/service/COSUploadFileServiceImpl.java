package com.inyaa.web.config.service;

import com.inyaa.base.util.FileUtil;
import com.inyaa.web.config.bean.SysOssConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class COSUploadFileServiceImpl implements UploadFileService {

    @Override
    public String upload(MultipartFile file, SysOssConfig config) {
        COSClient cosClient = null;
        try {
            COSCredentials cred = new BasicCOSCredentials(config.getAesKey(), config.getSecretKey());
            Region region = new Region(config.getDefined());
            ClientConfig clientConfig = new ClientConfig(region);
            cosClient = new COSClient(cred, clientConfig);
            String fileName = FileUtil.createSingleFilePath(config.getPath(), file.getOriginalFilename());
            PutObjectRequest putObjectRequest = new PutObjectRequest(config.getBucket(), fileName, file.getInputStream(), null);
            cosClient.putObject(putObjectRequest);
            return config.getUrl() + fileName;
        } catch (IOException e) {
            return "";
        } finally {
            if (cosClient != null) {
                cosClient.shutdown();
            }
        }
    }
}
