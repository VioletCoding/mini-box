package com.ghw.minibox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description 统一返回结果集
 * @date 2020/11/18
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class ReturnDto<T> {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
