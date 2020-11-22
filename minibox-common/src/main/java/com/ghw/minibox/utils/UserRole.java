package com.ghw.minibox.utils;

import lombok.Getter;

/**
 * @author Violet
 * @description 用户角色枚举
 * @date 2020/11/22
 */
@Getter
public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
