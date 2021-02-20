package com.ghw.minibox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("主键")
    private final String sub;

    @ApiModelProperty("签发时间")
    private final Long iat;

    @ApiModelProperty("过期时间")
    private final Long exp;

    @ApiModelProperty("JWT的ID")
    private final String jti;

    @ApiModelProperty("用户名")
    private final String username;

    @ApiModelProperty("用户id")
    private final Long userId;

    @ApiModelProperty("用户拥有的权限")
    private final List<String> authorities;
}
