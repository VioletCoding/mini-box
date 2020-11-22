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

    NORMAL("NORMAL"),
    INVALID("INVALID"),
    BANNED("BANNED");


    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

}
