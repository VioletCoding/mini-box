package com.ghw.minibox.utils;

import cn.hutool.core.util.IdUtil;
import lombok.Getter;

/**
 * @author Violet
 * @description 默认值枚举类
 * @date 2020/11/19
 */
@Getter
public enum DefaultColumn {

    //初始昵称,随机生成且唯一
    NICKNAME("用户_" + IdUtil.objectId()),
    //初始个人描述
    DESCRIPTION("写点什么吧"),
    //初始密码，随机且唯一
    PASSWORD(IdUtil.fastSimpleUUID()),
    //初始状态
    STATE("1"),
    //正常用户状态
    USER_STATE_NORMAL("NORMAL"),
    //非法用户状态
    USER_STATE_INVALID("INVALID"),
    //封禁用户状态
    USER_STATE_BANNED("BANNED");

    private final String message;

    DefaultColumn(String message) {
        this.message = message;
    }
}
