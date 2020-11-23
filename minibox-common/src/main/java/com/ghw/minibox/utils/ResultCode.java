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
    CREATED(201, "已创建"),
    ACCEPTED(202, "已接收你的请求"),

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方式不正确"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    HAS_BEEN_SENT(900, "已发送"),
    USER_EXIST(901, "用户已存在");


    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
