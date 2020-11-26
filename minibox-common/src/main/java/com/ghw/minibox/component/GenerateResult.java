package com.ghw.minibox.component;

import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 生成返回的统一结果集
 * @date 2020/11/18
 */
@Component
public class GenerateResult<T> {
    /**
     * 成功，但不返回数据
     *
     * @return ReturnDto
     */
    public ReturnDto<T> success() {
        return new ReturnDto<T>().setCode(ResultCode.OK.getCode()).setMessage(ResultCode.OK.getMessage());
    }

    /**
     * 成功，并返回数据
     *
     * @param data 数据体
     * @return ReturnDto
     */
    public ReturnDto<T> success(T data) {
        return new ReturnDto<T>()
                .setCode(ResultCode.OK.getCode())
                .setMessage(ResultCode.OK.getMessage())
                .setData(data);
    }

    /**
     * 成功，并返回多组数据，类型是map
     *
     * @param data map
     * @return ReturnDto
     */
    public ReturnDto<T> success(Map<String, T> data) {
        return new ReturnDto<T>()
                .setCode(ResultCode.OK.getCode())
                .setMessage(ResultCode.OK.getMessage())
                .setMapData(data);
    }

    /**
     * 成功，并返回多组数据，类型是list
     *
     * @param data list
     * @return ReturnDto
     */
    public ReturnDto<T> success(List<T> data) {
        return new ReturnDto<T>()
                .setCode(ResultCode.OK.getCode())
                .setMessage(ResultCode.OK.getMessage())
                .setListData(data);
    }

    /**
     * 失败，响应失败信息，不带数据
     *
     * @return ReturnDto
     */
    public ReturnDto<T> fail() {
        return new ReturnDto<T>()
                .setCode(ResultCode.BAD_REQUEST.getCode())
                .setMessage(ResultCode.BAD_REQUEST.getMessage());
    }

    /**
     * 自定义返回信息，不带数据
     *
     * @param code    响应码
     * @param message 响应信息
     * @return ReturnDto
     */
    public ReturnDto<T> custom(int code, String message) {
        return new ReturnDto<T>()
                .setCode(code)
                .setMessage(message);
    }

    /**
     * 自定义返回信息，带数据
     *
     * @param code    响应码
     * @param message 响应信息
     * @param data    数据
     * @return ReturnDto
     */
    public ReturnDto<T> custom(int code, String message, T data) {
        return new ReturnDto<T>()
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

}
