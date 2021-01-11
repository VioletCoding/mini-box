package com.ghw.minibox.utils;

import lombok.Getter;

/**
 * @author Violet
 * @description 用户角色枚举
 * @date 2020/11/22
 */
@Getter
public enum UserRole {

    USER("USER", 10000L),
    ADMIN("ADMIN", 10001L);

    private final String role;
    private final Long roleId;

    UserRole(String role, Long roleId) {
        this.role = role;
        this.roleId = roleId;
    }
}
