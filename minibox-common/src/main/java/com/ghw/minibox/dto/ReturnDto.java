package com.ghw.minibox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Violet
 * @description 统一返回结果集
 * @date 2020/11/18
 */
@Data
@Accessors(chain = true)
public class ReturnDto<T> {

    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("响应信息")
    private String message;

    @ApiModelProperty("数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
