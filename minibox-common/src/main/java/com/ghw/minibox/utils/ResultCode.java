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
    UNAUTHORIZED(401, "用户未登录"),
    AUTH_CODE_ERROR(402, "验证码错误"),

    FORBIDDEN(403, "禁止访问"),

    NOT_FOUND(404, "请求的资源未找到"),

    METHOD_NOT_ALLOWED(405, "请求方式不正确"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    HAS_BEEN_SENT(900, "邮件已发送"),

    USER_EXIST(901, "用户已存在"),

    USER_BANNED(902, "用户被封禁"),

    USER_AUTH_FAIL(903, "用户名或密码不正确"),

    USER_ILLEGAL(904, "用户非法"),

    RECORD_DISABLE(905, "该条记录不可用"),

    TID_IS_NULL(906, "当type=TC时，tid不能为空"),

    GID_IS_NULL(907, "当type=GC时，gid不能为空"),

    SCORE_IS_NULL(908, "当type=GC时，score不能为空"),

    REPLY_IN_POST_IS_NULL(909, "当type=TR时，replyInPost不能为空"),

    REPLY_IN_GAME_IS_NULL(910, "当type=GR时，replyInGame不能为空"),

    GAME_NOT_FOUND(911, "未找到该游戏"),

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
