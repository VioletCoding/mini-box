package com.ghw.minibox.utils;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.concurrent.*;

/**
 * @author Violet
 * @description 七牛云对象存储工具类
 * @date 2020/11/23
 */
@Component
@Slf4j
public class QiNiuUtil {

    /**
     * 异步方法
     * 七牛云上传，需要指定accessKey和secretKey以及bucket还有输入流
     * <p>
     * Configuration 构造一个带指定 Region 对象的配置类
     * <p>
     * UploadManager 上传管理器
     * <p>
     * Response 解析上传成功的结果
     * <p>
     * Callable<DefaultPutRet> 实现Callable，为了拿异步回调
     * FutureTask<DefaultPutRet> 获取异步回调
     *
     * @param ak          公钥
     * @param sk          私钥
     * @param bucket      对象空间
     * @param inputStream 输入流
     * @return 异步回调，七牛云上传后的回调
     * @throws QiniuException 七牛云异常
     */
    @Async
    public Future<DefaultPutRet> upload(String ak, String sk, String bucket, InputStream inputStream) throws QiniuException {
        log.info("进入upload");
        log.info("传入的ak为==>{},传入的sk为==>{},传入的bucket为==>{}", ak, sk, bucket);
        Auth auth = Auth.create(ak, sk);
        String uploadToken = auth.uploadToken(bucket);
        log.info("uploadToken为==>{}", uploadToken);
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager um = new UploadManager(cfg);
        Response response = um.put(inputStream, IdUtil.fastSimpleUUID(), uploadToken, null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        Callable<DefaultPutRet> callable = () -> objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
        FutureTask<DefaultPutRet> futureTask = new FutureTask<>(callable);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);
        executorService.shutdown();
        log.info("结束upload");
        return futureTask;
    }
}
