package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.model.OrderModel;
import com.ghw.minibox.service.MbpOrderService;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 订单控制层
 * @date 2021/1/9
 */
@RestController
@RequestMapping("orderApi")
public class MbpOrderController {
    @Resource
    private MbpOrderService orderService;

    /**
     * 生成订单
     *
     * @param orderModel 实体
     * @return 订单信息
     */
    @ApiOperation("生成订单")
    @PostMapping("add")
    public ResultVo generateOrder(@RequestBody @Validated OrderModel orderModel) throws JsonProcessingException {
        Object generateOrder = orderService.create(orderModel);
        return Result.success(generateOrder);
    }

    /**
     * 下单、确认订单
     *
     * @param orderModel 实体
     * @return 是否成功
     */
    @ApiOperation("确认订单")
    @PostMapping("confirm")
    public ResultVo confirmOrder(@RequestBody @Validated OrderModel orderModel) {
        if (orderModel.getOrderId() == null) {
            return Result.fail("订单ID为空");
        }
        boolean b = orderService.submit(orderModel);
        return Result.successFlag(b);
    }

    /**
     * 取消订单
     *
     * @param orderModel 实体
     * @return 是否成功
     */
    @ApiOperation("取消订单")
    @PostMapping("cancel")
    public ResultVo cancelOrder(@RequestBody @Validated OrderModel orderModel) {
        boolean b = orderService.cancelOrder(orderModel);
        return Result.successFlag(b);
    }

}
