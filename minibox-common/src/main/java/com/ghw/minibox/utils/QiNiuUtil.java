package com.ghw.minibox.utils;

import com.ghw.minibox.component.impl.UpCompletionHandlerImpl;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Violet
 * @description 七牛云对象存储工具类
 * @date 2020/11/23
 */
@Component
@Slf4j
public class QiNiuUtil {
    /**
     * 异步七牛云上传
     * <p>
     * Configuration 构造一个带指定 Region 对象的配置类
     * <p>
     * UploadManager 上传管理器
     * <p>
     * Response 解析上传成功的结果
     * <p>
     *
     * @param ak     公钥
     * @param sk     私钥
     * @param bucket 对象空间
     * @param key    文件名
     * @param bytes  字节数组
     * @throws QiniuException -
     */
    public void asyncUpload(String ak, String sk, String bucket, String key, byte[] bytes) throws IOException {
        Auth auth = Auth.create(ak, sk);
        String uploadToken = auth.uploadToken(bucket);
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager um = new UploadManager(cfg);
        um.asyncPut(bytes, key, uploadToken, null, null, false, new UpCompletionHandlerImpl());
    }

    public void asyncUpload(String ak, String sk, String bucket, String key, InputStream inputStream) throws IOException {
        Auth auth = Auth.create(ak, sk);
        String uploadToken = auth.uploadToken(bucket);
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager um = new UploadManager(cfg);
        um.put(inputStream, key, uploadToken, null, null);
    }

    /**
     * 同步流式上传文件
     *
     * @param ak          公钥
     * @param sk          私钥
     * @param bucket      对象空间
     * @param key         文件名
     * @param inputStream 输入流
     * @throws IOException -
     */
    public void syncUpload(String ak, String sk, String bucket, String key, InputStream inputStream) throws IOException {
        Auth auth = Auth.create(ak, sk);
        String uploadToken = auth.uploadToken(bucket);
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager um = new UploadManager(cfg);
        um.put(inputStream, key, uploadToken, null, null);
    }

}
