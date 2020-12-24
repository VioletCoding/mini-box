package com.ghw.minibox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2020/11/15
 */
@Builder(toBuilder = true)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayloadDto {
    /**
     * 主键
     */
    private final String sub;
    /**
     * 签发时间
     */
    private final Long iat;
    /**
     * 过期时间
     */
    private final Long exp;
    /**
     * JWT的ID
     */
    private final String jti;
    /**
     * 用户名
     */
    private final String username;
    /**
     * 用户拥有的权限
     */
    private final List<String> authorities;
}
