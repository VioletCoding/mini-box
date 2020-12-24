package com.ghw.minibox.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Violet
 * @description
 * @date 2020/12/24
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDto {
    private Integer code;
    private String message;
    private Object data;
}
