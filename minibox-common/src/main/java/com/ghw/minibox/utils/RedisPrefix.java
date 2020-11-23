package com.ghw.minibox.utils;

import lombok.Getter;

/**
 * @author Violet
 * @description Redis用于区分key的前缀 枚举
 * @date 2020/11/23
 */
@Getter
public enum RedisPrefix {

    /**
     * 已经注册成功的用户
     */
    USER_EXIST("user_exist_"),
    /**
     * 临时的，用于存储5分钟的验证码
     */
    USER_TEMP("user_temp_");


    private final String prefix;

    RedisPrefix(String prefix) {
        this.prefix = prefix;
    }
}
