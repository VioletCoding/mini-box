package com.ghw.minibox.utils;

import com.ghw.minibox.dto.ReturnDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 全局异常管理
 * @date 2020/11/19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private ReturnDto<Object> returnDto;

    @ExceptionHandler(NullPointerException.class)
    public ReturnDto<Object> nullPointException(NullPointerException e) {
        log.error("出现异常 {} , 原因如下 {} , 栈信息如下==> \n ", e.getMessage(), e.getCause());
        e.printStackTrace();
        return returnDto
                .setCode(ResultCode.INTERNAL_SERVER_ERROR.getCode())
                .setMessage(ResultCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}
