package com.ghw.minibox.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2020/11/15
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PayloadDto {
    /**
     * 主键
     */
    private String sub;
    /**
     * 签发时间
     */
    private Long iat;
    /**
     * 过期时间
     */
    private Long exp;
    /**
     * JWT的ID
     */
    private String jti;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户拥有的权限
     */
    private List<String> authorities;
}
