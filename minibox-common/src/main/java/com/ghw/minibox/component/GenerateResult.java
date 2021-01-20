package com.ghw.minibox.component;

import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description 生成返回的统一结果集
 * @date 2020/11/18
 */
@Component
public class GenerateResult<T> {

    /**
     * 成功，但不返回数据
     */
    public ReturnDto<T> success() {
        return new ReturnDto<T>().setCode(ResultCode.OK.getCode()).setMessage(ResultCode.OK.getMessage());
    }

    /**
     * 成功，并返回数据
     */
    public ReturnDto<T> success(T data) {
        return new ReturnDto<T>().setCode(ResultCode.OK.getCode()).setMessage(ResultCode.OK.getMessage()).setData(data);
    }

    /**
     * 失败，响应失败信息，不带数据
     */
    public ReturnDto<T> fail() {
        return new ReturnDto<T>().setCode(ResultCode.BAD_REQUEST.getCode()).setMessage(ResultCode.BAD_REQUEST.getMessage());
    }

    public ReturnDto<T> fail(String message){
        return new ReturnDto<T>().setCode(ResultCode.BAD_REQUEST.getCode()).setMessage(message);
    }

    public ReturnDto<T> fail(ResultCode r) {
        return new ReturnDto<T>().setCode(r.getCode()).setMessage(r.getMessage());
    }

    public ReturnDto<T> fail(ResultCode r, T data) {
        return new ReturnDto<T>().setCode(r.getCode()).setMessage(r.getMessage()).setData(data);
    }

    /**
     * 自定义返回信息，不带数据
     */
    public ReturnDto<T> custom(int code, String message) {
        return new ReturnDto<T>().setCode(code).setMessage(message);
    }


}
