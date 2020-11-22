package com.ghw.minibox.utils;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

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
        log.error("出现异常 {} , 原因如下 {} , 栈信息如下==> \n ", e.getMessage(), e.getCause());
        e.printStackTrace();
        return gr.fail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReturnDto<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("出现异常 {} , 原因如下 {} , 栈信息如下==> \n ", e.getMessage(), e.getCause());
        e.printStackTrace();
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "参数校验失败", objectError.getDefaultMessage());
    }

    @ExceptionHandler(EmailException.class)
    public ReturnDto<String> emailException(EmailException e) {
        log.error("出现异常 {} , 原因如下 {} , 栈信息如下==> \n ", e.getMessage(), e.getCause());
        e.printStackTrace();
        return gr.custom(ResultCode.BAD_REQUEST.getCode(), "邮件发送失败");
    }

    @ExceptionHandler(InterruptedException.class)
    public ReturnDto<String> interruptedException(InterruptedException e) {
        log.error("出现异常 {} , 原因如下 {} , 栈信息如下==> \n ", e.getMessage(), e.getCause());
        e.printStackTrace();
        return gr.fail();
    }

    @ExceptionHandler(ExecutionException.class)
    public ReturnDto<String> executionException(ExecutionException e) {
        log.error("出现异常 {} , 原因如下 {} , 栈信息如下==> \n ", e.getMessage(), e.getCause());
        e.printStackTrace();
        return gr.fail();
    }

}
