package com.ghw.minibox.component.impl;

import com.ghw.minibox.dto.QiNiuPutRet;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UpCompletionHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Violet
 * @description 实现七牛云异步上传接口，上传完成后，SDK会回调这个接口的complete方法
 * @date 2020/11/28
 */
@Slf4j
public class UpCompletionHandlerImpl implements UpCompletionHandler {
    /**
     * 七牛云上传异步回调函数
     *
     * @param key 文件key
     * @param r   响应类
     */
    @Override
    public void complete(String key, Response r) {
        try {
            QiNiuPutRet qiNiuPutRet = r.jsonToObject(QiNiuPutRet.class);
            log.info("本次上传已完成,异步上传文件key==>{} \n 异步上传回调==>{}", key, qiNiuPutRet.toString());
        } catch (QiniuException e) {
            e.printStackTrace();
            try {
                QiNiuPutRet qiNiuPutRet = e.response.jsonToObject(QiNiuPutRet.class);
                log.error("七牛云返回错误信息==>{}", qiNiuPutRet);
            } catch (QiniuException qiniuException) {
                qiniuException.printStackTrace();
                log.error("七牛云返回的错误信息中的错误信息==>{}", qiniuException.response.getInfo());
                log.error("异步上传文件失败，时间==>{}", new Date().toString());
            }
            log.error("异步上传文件失败，时间==>{}", new Date().toString());
        }
    }
}
