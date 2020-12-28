package com.ghw.minibox.utils;

import com.ghw.minibox.component.impl.UpCompletionHandlerImpl;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${qiNiu.accessKey}")
    private String ak;
    @Value("${qiNiu.secretKey}")
    private String sk;
    @Value("${qiNiu.bucket}")
    private String bucket;


    /**
     * 异步上传
     *
     * @param key   文件名
     * @param bytes 字节数组
     * @throws QiniuException -
     */
    public void asyncUpload(String key, byte[] bytes) throws IOException {
        getDefaultUploadManager().asyncPut(bytes, key, getDefaultAuth().uploadToken(this.bucket), null, null, false, new UpCompletionHandlerImpl());
    }

    /**
     * 使用默认方式上传
     *
     * @param key   文件名
     * @param bytes 字节数组
     */
    public void syncUpload(String key, byte[] bytes) throws IOException {
        getDefaultUploadManager().put(bytes, key, getDefaultAuth().uploadToken(this.bucket));
    }

    /**
     * 流上传
     *
     * @param key         文件名
     * @param inputStream 文件流
     */
    public void syncUpload(String key, InputStream inputStream) throws IOException {
        getDefaultUploadManager().put(inputStream, key, getDefaultAuth().uploadToken(this.bucket), null, null);
    }

    /**
     * 删除空间中的文件
     * @param key 文件名
     */
    public void delete(String key) throws QiniuException {
        getSDefaultBucketManager().delete(this.bucket,key);
    }

    /**
     * 获取默认认证器
     *
     * @return Auth
     */
    private Auth getDefaultAuth() {
        return Auth.create(this.ak, this.sk);
    }

    /**
     * 获取默认配置
     *
     * @return Configuration
     */
    private Configuration getDefaultConfiguration() {
        return new Configuration(Region.huanan());
    }

    /**
     * 获取空间管理器
     *
     * @return BucketManager
     */
    private BucketManager getSDefaultBucketManager() {
        return new BucketManager(getDefaultAuth(), getDefaultConfiguration());
    }

    /**
     * 获取上传管理器
     *
     * @return UploadManager
     */
    private UploadManager getDefaultUploadManager() {
        return new UploadManager(getDefaultConfiguration());
    }

}
