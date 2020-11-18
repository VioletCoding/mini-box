package com.ghw.minibox.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 统一返回结果集
 * @date 2020/11/18
 */
@Data
@Accessors(chain = true)
@ToString
public class ReturnDto<T> {
    /**
     * 响应码
     */
    @NotNull(message = "请输入响应码")
    private int code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    /**
     * 多组数据，类型是map
     */
    private Map<String, T> mapData;

    /**
     * 多组数据，类型是list
     */
    private List<T> listData;


}
