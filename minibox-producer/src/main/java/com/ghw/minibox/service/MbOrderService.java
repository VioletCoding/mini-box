package com.ghw.minibox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.entity.MbOrder;

import java.text.ParseException;

/**
 * @author Violet
 * @description
 * @date 2020/12/31
 */

public interface MbOrderService {
    /**
     * 生成订单
     *
     * @param mbOrder 实例
     * @return 订单信息
     */
    MbOrder generateOrder(MbOrder mbOrder) throws JsonProcessingException, ParseException;

    /**
     * 下订单成功后存储
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    boolean saveOrder(MbOrder mbOrder);

    /**
     * 取消订单
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    boolean cancelOrder(MbOrder mbOrder);

}
