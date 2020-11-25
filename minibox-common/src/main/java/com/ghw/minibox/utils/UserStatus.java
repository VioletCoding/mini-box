package com.ghw.minibox.utils;

import lombok.Getter;

/**
 * @author Violet
 * @description 用户状态枚举
 * 用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）
 * @date 2020/11/22
 */

@Getter
public enum UserStatus {

    NORMAL("NORMAL", "正常"),
    INVALID("INVALID", "非法用户"),
    BANNED("BANNED", "用户被封禁");


    private final String status;
    private final String message;

    UserStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

}
