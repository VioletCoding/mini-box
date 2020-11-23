package com.ghw.minibox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghw.minibox.utils.ResultCode;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class ReturnDto<T> {
    /**
     * 响应码
     */
    @NotNull(message = "请输入响应码")
    private Integer code;
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

    /**
     * 简单返回，直接取对应的枚举，没必要每次都setCode、setMessage、但不适用于带数据的返回
     */
    private ResultCode simple;


}
