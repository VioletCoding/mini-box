package com.ghw.minibox.utils;

import lombok.Getter;

/**
 * @author Violet
 * @description 统一返回枚举类
 * @date 2020/11/19
 */
@Getter
public enum ResultCode {

    OK(200, "成功"),

    BAD_REQUEST(400, "错误的请求"),

    UNAUTHORIZED(401, "用户未登录"),

    AUTH_CODE_ERROR(402, "验证码错误"),

    TID_IS_NULL(906, "当type=TC时，tid不能为空"),

    GID_IS_NULL(907, "当type=GC时，gid不能为空"),

    SCORE_IS_NULL(908, "当type=GC时，score不能为空"),

    ORDER_GENERATE_FAIL(1001, "订单生成失败"),

    ORDER_NOT_FOUND(1002, "未找到订单"),

    ORDER_CANCEL(1003, "订单已被取消");


    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
