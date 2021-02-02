package com.ghw.minibox.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Violet
 * @description 订单工具类
 * @date 2020/12/31
 */
@Component
@Slf4j
public class OrderUtil {

    public final String SUCCESS = "1";
    public final String NOT_SUCCESS = "0";

    /**
     * 获取8位订单号
     */
    public Long getOrderId() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            sb.append(random.nextInt(9));
        }
        return Long.parseLong(sb.toString());
    }

}
