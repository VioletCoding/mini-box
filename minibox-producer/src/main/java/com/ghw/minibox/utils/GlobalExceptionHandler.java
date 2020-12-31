package com.ghw.minibox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
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

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author Violet
 * @description 全局异常管理
 * @date 2020/11/19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private GenerateResult<String> gr;

    @ExceptionHandler(NullPointerException.class)
    public ReturnDto<String> nullPointException(NullPointerException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.fail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReturnDto<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), objectError.getDefaultMessage());
    }

    @ExceptionHandler(EmailException.class)
    public ReturnDto<String> emailException(EmailException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "没有找到此邮箱");
    }

    @ExceptionHandler(InterruptedException.class)
    public ReturnDto<String> interruptedException(InterruptedException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.fail();
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ReturnDto<String> jsonProcessingException(JsonProcessingException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "Json解析失败");
    }

    @ExceptionHandler(JOSEException.class)
    public ReturnDto<String> jOSEException(JOSEException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "jwt签发失败");
    }


    @ExceptionHandler(QiniuException.class)
    public ReturnDto<String> qiNiuException(QiniuException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "文件上传失败");
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ReturnDto<String> unSupportedEncodingException(UnsupportedEncodingException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "文件编码解析失败");
    }


    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ReturnDto<String> fileSizeLimitExceededException(FileSizeLimitExceededException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "文件大小超出限制，单个文件最大3MB");
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ReturnDto<String> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "文件大小超出限制，总文件大小最大30MB");
    }

    @ExceptionHandler(MyException.class)
    public ReturnDto<String> MyException(MyException e) {
        log.error("异常=>", e);
        e.printStackTrace();
        return gr.custom(ResultCode.GAME_NOT_FOUND.getCode(), e.getMessage());
    }

}
