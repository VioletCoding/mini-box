package com.ghw.minibox.utils;

/**
 * @author Violet
 * @description 自定义异常
 * @date 2020/12/31
 */

public class MyException extends RuntimeException{

    private String message;

    public MyException(String message){
        super(message);
    }
}
