package com.ghw.minibox.utils;

import com.ghw.minibox.vo.ResultVo;

/**
 * @author Violet
 * @description 生成返回的统一结果集
 * @date 2020/11/18
 */

public class Result {
    public static ResultVo success() {
        return new ResultVo(ResultCode.OK.getCode(), ResultCode.OK.getMessage());
    }

    public static ResultVo successFlag(boolean b) {
        if (b) {
            return new ResultVo(ResultCode.OK.getCode(), ResultCode.OK.getMessage());
        }
        return new ResultVo(ResultCode.BAD_REQUEST.getCode(), ResultCode.BAD_REQUEST.getMessage());
    }

    public static ResultVo success(String message) {
        return new ResultVo(ResultCode.OK.getCode(), message);
    }

    public static ResultVo success(Object data) {
        return new ResultVo(ResultCode.OK.getCode(), ResultCode.OK.getMessage(), data);
    }

    public static ResultVo fail() {
        return new ResultVo().setCode(ResultCode.BAD_REQUEST.getCode()).setMessage(ResultCode.BAD_REQUEST.getMessage());
    }

    public static ResultVo fail(String message) {
        return new ResultVo().setCode(ResultCode.BAD_REQUEST.getCode()).setMessage(message);
    }

    public static ResultVo fail(ResultCode r) {
        return new ResultVo().setCode(r.getCode()).setMessage(r.getMessage());
    }
}
