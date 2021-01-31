package com.ghw.minibox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Violet
 * @description 统一返回结果集
 * @date 2020/11/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultVo {

    @ApiModelProperty("响应码")
    private int code;

    @ApiModelProperty("响应信息")
    private String message;

    @ApiModelProperty("数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResultVo(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
