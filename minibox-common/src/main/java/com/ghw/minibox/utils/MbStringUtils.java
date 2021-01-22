package com.ghw.minibox.utils;

/**
 * @author Violet
 * @description 自定义字符串工具类
 * @date 2021/1/22
 */

public class MbStringUtils {
    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }
}
