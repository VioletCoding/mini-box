package com.ghw.minibox.utils;

import cn.hutool.core.util.IdUtil;
import lombok.Getter;

/**
 * @author Violet
 * @description 默认值枚举类
 * @date 2020/11/19
 */
@Getter
public enum DefaultUserInfoEnum {


    NICKNAME("用户_" + IdUtil.objectId());

    private final String message;

    DefaultUserInfoEnum(String message) {
        this.message = message;
    }
}
