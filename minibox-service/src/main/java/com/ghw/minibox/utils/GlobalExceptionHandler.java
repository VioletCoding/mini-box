package com.ghw.minibox.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.vo.ResultVo;
import com.ghw.minibox.exception.MiniBoxException;
import com.nimbusds.jose.JOSEException;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author Violet
 * @description 全局异常管理，如果出现异常，那么此类会接管Controller的返回，返回对应的异常响应信息
 * 项目做完后，记得把e.printStackTrace();去掉，不然日志文件会很大，当然小项目也没必要。
 * @date 2020/11/19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 防止空指针是每一个程序员的责任，必须做好入参校验
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultVo nullPointException(NullPointerException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * Hibernate-Validator 异常，在Controller标注了@Validated注解的话
     * 如果传入的实体里的某个入参与实体里要求检验的参数规则不一样，那么抛出此异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return Result.custom(ResultCode.BAD_REQUEST.getCode(), objectError.getDefaultMessage());
    }


    /**
     * 邮件异常，如果抛出此异常，大概率是对应邮箱服务器里未找到对应的邮箱账户，那么会抛出此异常
     * 本系统通过QQ邮箱的SMTP/POP3进行邮件投递，理论上支持所有其他邮箱服务器的邮件投递
     */
    @ExceptionHandler(EmailException.class)
    public ResultVo emailException(EmailException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.custom(ResultCode.BAD_REQUEST.getCode(), "该邮箱不存在");
    }

    /**
     * 线程异常
     * 在活动之前或活动期间，线程在等待，休眠或以其他方式占用并且线程被中断时抛出。
     * 有时，一种方法可能希望测试当前线程是否已被中断，如果已中断，则立即抛出此异常。
     */
    @ExceptionHandler(InterruptedException.class)
    public ResultVo interruptedException(InterruptedException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * Json解析异常，如果使用ObjectMapper解析Json或者是Jackson自动解析Json时，解析失败会抛出此异常
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResultVo jsonProcessingException(JsonProcessingException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.custom(ResultCode.BAD_REQUEST.getCode(), "Json解析失败");
    }

    /**
     * Json Web Token （JWT）解析异常
     * 如果调用 {@link com.ghw.minibox.component.NimbusJoseJwt}里的{@link NimbusJoseJwt#verifyTokenByHMAC(java.lang.String)}方法
     * 解析Token失败时，会抛出此异常。
     * 一般抛出此异常的原因：1、token内容被篡改 2、token过期 3、非本系统签名的算法生成的token，也就是非法token
     */
    @ExceptionHandler(JOSEException.class)
    public ResultVo jOSEException(JOSEException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.custom(ResultCode.BAD_REQUEST.getCode(), "jwt签发失败");
    }


    /**
     * 七牛云对象存储 异常
     * 抛出此异常的原因可能：1、网络问题，未能将文件上传到七牛云 2、七牛云服务器问题 3、文件不合法 4、空间已满 5、其他
     */
    @ExceptionHandler(QiniuException.class)
    public ResultVo qiNiuException(QiniuException e) {
        log.error("异常=>{}", e.response.toString());
        return Result.custom(ResultCode.BAD_REQUEST.getCode(), "文件上传失败");
    }

    /**
     * 不支持的字符编码 异常
     * 抛出此异常的原因可能：1、文件上传的入参有问题，不是form-data 2、响应头的字符编码写错了（因为是字符串） 3、其他
     */
    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResultVo unSupportedEncodingException(UnsupportedEncodingException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.custom(ResultCode.BAD_REQUEST.getCode(), "文件编码解析失败");
    }


    /**
     * 单个文件上传大小超出限制 异常
     * 抛出此异常的原因可能：单个文件大小超出了系统设置的大小，前端应该在请求服务器前先检查一次文件约定的大小
     */
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResultVo fileSizeLimitExceededException(FileSizeLimitExceededException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.custom(ResultCode.BAD_REQUEST.getCode(), "文件大小超出限制，单个文件最大3MB");
    }

    /**
     * 总文件上传大小超出限制 异常
     * 抛出此异常的原因可能：单个文件大小 合法 ，但是多文件上传超出了系统设置的单次请求的文件总大小，前端应该在请求服务器前先检查一次文件约定的大小
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultVo maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.fail(ResultCode.BAD_REQUEST, "文件大小超出限制，总文件大小最大30MB");
    }


    /**
     * 运行时异常，开发时记得打印栈信息，不然被捕获了出问题了都不知道怎么查
     */
    //@ExceptionHandler(RuntimeException.class)
    //public ReturnDto buyFlagException(RuntimeException e) {
    //    log.error("异常=>{}", new Date().toString());
    //    e.printStackTrace();
    //    return Result.custom(ResultCode.BAD_REQUEST.getCode(), e.getMessage());
    //}
    @ExceptionHandler(MiniBoxException.class)
    public ResultVo miniBoxException(MiniBoxException e) {
        log.error("异常=>{}", new Date().toString());
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }
}
