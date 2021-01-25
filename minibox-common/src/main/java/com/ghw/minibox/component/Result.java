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
public class Result {

    public static ReturnDto success() {
        return new ReturnDto(ResultCode.OK.getCode(),ResultCode.OK.getMessage());
    }

    public static ReturnDto success(Object data) {
        return new ReturnDto(ResultCode.OK.getCode(), ResultCode.OK.getMessage(), data);
    }

    public static ReturnDto fail() {
        return new ReturnDto().setCode(ResultCode.BAD_REQUEST.getCode()).setMessage(ResultCode.BAD_REQUEST.getMessage());
    }

    public static ReturnDto fail(String message) {
        return new ReturnDto().setCode(ResultCode.BAD_REQUEST.getCode()).setMessage(message);
    }

    public static ReturnDto fail(ResultCode r) {
        return new ReturnDto().setCode(r.getCode()).setMessage(r.getMessage());
    }

    public static ReturnDto fail(ResultCode r, Object data) {
        return new ReturnDto(r.getCode(), r.getMessage(), data);
    }

    public static ReturnDto custom(int code, String message) {
        return new ReturnDto().setCode(code).setMessage(message);
    }


}
