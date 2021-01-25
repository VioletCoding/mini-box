package com.ghw.minibox.component.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UpCompletionHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Violet
 * @description 实现七牛云异步上传接口，上传完成后，SDK会回调这个接口的complete方法
 * @date 2020/11/28
 */
@Slf4j
public class UpCompletionHandlerImpl implements UpCompletionHandler {

    /**
     * 七牛云响应类
     */
    @Data
    @Accessors(chain = true)
    static class QiNiuPutRet {
        private String key;
        private String hash;
        private String bucket;
        private long fsize;
    }


    /**
     * 七牛云上传异步回调函数
     *
     * @param key 文件名
     * @param r   七牛云响应类
     */
    @Override
    public void complete(String key, Response r) {
        try {
            QiNiuPutRet qiNiuPutRet = r.jsonToObject(QiNiuPutRet.class);
            log.info("七牛云异步上传回调=>{}", qiNiuPutRet.toString());
        } catch (QiniuException e) {
            log.error("七牛云上传错误信息=>{}", e.getMessage());
            try {
                QiNiuPutRet qiNiuPutRet = e.response.jsonToObject(QiNiuPutRet.class);
                log.error("七牛云上传错误信息=>{}", qiNiuPutRet);
            } catch (QiniuException qiniuException) {
                log.error("七牛云上传错误信息=={}", qiniuException.response.getInfo());
            }
        }
    }
}
