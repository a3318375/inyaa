package com.inyaa.web.config.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.inyaa.base.util.FileUtil;
import com.inyaa.web.config.bean.SysOssConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ALiYunOSSUploadFileServiceImpl implements UploadFileService {

    @Override
    public String upload(MultipartFile file, SysOssConfig config) {
        OSS ossClient = new OSSClientBuilder().build(config.getDefined(), config.getAesKey(), config.getSecretKey());
        try {
            String fileName = FileUtil.createSingleFilePath(config.getPath(), file.getOriginalFilename());
            PutObjectRequest putObjectRequest = new PutObjectRequest(config.getBucket(), fileName, file.getInputStream());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            putObjectRequest.setMetadata(metadata);
            ossClient.putObject(putObjectRequest);
            return config.getUrl() + fileName;
        } catch (IOException e) {
            return "";
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
