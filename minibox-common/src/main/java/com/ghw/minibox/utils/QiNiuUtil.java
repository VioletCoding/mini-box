package com.ghw.minibox.utils;

import com.ghw.minibox.component.impl.UpCompletionHandlerImpl;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
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
    public static final String defaultPhotoLink = "http://qnbtg7yhm.hn-bkt.clouddn.com/default.jpg";
    public static final String qiNiuLink = "http://qnbtg7yhm.hn-bkt.clouddn.com/";
    private static final String ak = "OeZ3mKc0cwT2RVq-i6tmtmPj55xOp1NdT_6U4NHU";
    private static final String sk = "NG3slWtnHU_lFhI7FimGzfF_hke9LTq9yHDn7OFV";
    private static final String bucket = "minibox-bucket-3";
    private static final String link = "http://qnbtg7yhm.hn-bkt.clouddn.com/";

    /**
     * 异步上传
     *
     * @param key   文件名
     * @param bytes 字节数组
     * @throws QiniuException -
     */
    public String asyncUpload(String key, byte[] bytes) throws IOException {
        getDefaultUploadManager().asyncPut(bytes, key, getDefaultAuth().uploadToken(bucket), null, null, false, new UpCompletionHandlerImpl());
        return link + key;
    }

    /**
     * 使用默认方式上传，同步
     *
     * @param key   文件名
     * @param bytes 字节数组
     */
    public String syncUpload(String key, byte[] bytes) throws IOException {
        getDefaultUploadManager().put(bytes, key, getDefaultAuth().uploadToken(bucket));
        return link + key;
    }

    /**
     * 流上传 同步
     *
     * @param key         文件名
     * @param inputStream 文件流
     */
    public String syncUpload(String key, InputStream inputStream) throws IOException {
        getDefaultUploadManager().put(inputStream, key, getDefaultAuth().uploadToken(bucket), null, null);
        return link + key;
    }

    /**
     * 删除空间中的文件 同步
     *
     * @param key 文件名
     */
    public boolean delete(String key) throws QiniuException {
        Response response = getSDefaultBucketManager().delete(bucket, key);
        return response.isOK();
    }

    /**
     * 获取默认认证器
     *
     * @return Auth
     */
    private Auth getDefaultAuth() {
        return Auth.create(ak, sk);
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
