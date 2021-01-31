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

    BAD_REQUEST(400, "失败"),

    UNAUTHORIZED(401, "未授权"),

    AUTH_CODE_ERROR(402, "验证码错误"),

    TID_IS_NULL(906, "当type=TC时，tid不能为空"),

    GID_IS_NULL(907, "当type=GC时，gid不能为空"),

    SCORE_IS_NULL(908, "当type=GC时，score不能为空"),

    ORDER_GENERATE_FAIL(1001, "订单生成失败"),

    ORDER_CREATED(1002, "订单已创建"),

    ORDER_NOT_FOUND(1003, "订单未找到"),

    ORDER_CANCEL(1004, "订单已被取消"),

    ORDER_PAYED(1005, "您已拥有此游戏"),

    GAME_CANT_BE_BUY(1006, "游戏暂时不可购买");


    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
