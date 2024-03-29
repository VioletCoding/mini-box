package com.ghw.minibox.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Violet
 * @description Aop操作日志工具bean
 * @date 2020/11/23
 */
@Data
public class AopBean {
    private String operation;
    private Long time;
    private String method;
    private String param;
}
