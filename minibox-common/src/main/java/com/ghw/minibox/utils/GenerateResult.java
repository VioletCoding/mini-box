package com.ghw.minibox.utils;

import com.ghw.minibox.dto.ReturnDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 生成返回的统一结果集
 * @date 2020/11/18
 */

@Component
public class GenerateResult<T> {
    @Resource
    private ReturnDto<T> returnDto;

    /**
     * 成功，但不返回数据
     *
     * @return ReturnDto
     */
    public ReturnDto<T> success() {
        return returnDto
                .setCode(HttpStatus.OK.value())
                .setMessage(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * 成功，并返回数据
     *
     * @param data 数据体
     * @return ReturnDto
     */
    public ReturnDto<T> success(T data) {
        return returnDto
                .setCode(HttpStatus.OK.value())
                .setMessage(HttpStatus.OK.getReasonPhrase())
                .setData(data);
    }

    /**
     * 成功，并返回多组数据，类型是map
     *
     * @param data map
     * @return ReturnDto
     */
    public ReturnDto<T> success(Map<String, T> data) {
        return returnDto
                .setCode(HttpStatus.OK.value())
                .setMessage(HttpStatus.OK.getReasonPhrase())
                .setMapData(data);
    }

    /**
     * 成功，并返回多组数据，类型是list
     *
     * @param data list
     * @return ReturnDto
     */
    public ReturnDto<T> success(List<T> data) {
        return returnDto
                .setCode(HttpStatus.OK.value())
                .setMessage(HttpStatus.OK.getReasonPhrase())
                .setListData(data);
    }

    /**
     * 失败，响应失败信息，不带数据
     *
     * @return ReturnDto
     */
    public ReturnDto<T> fail() {
        return returnDto
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    /**
     * 自定义返回信息，不带数据
     *
     * @param code    响应码
     * @param message 响应信息
     * @return ReturnDto
     */
    public ReturnDto<T> custom(int code, String message) {
        return returnDto
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
        return returnDto
                .setCode(code)
                .setMessage(message)
                .setData(data);
    }

}
