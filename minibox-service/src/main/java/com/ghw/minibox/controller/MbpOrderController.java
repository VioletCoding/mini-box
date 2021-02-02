package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.model.OrderModel;
import com.ghw.minibox.service.impl.MbpOrderServiceImpl;
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
    private MbpOrderServiceImpl orderService;

    @ApiOperation("生成订单")
    @PostMapping("add")
    public ResultVo generateOrder(@RequestBody @Validated OrderModel orderModel) throws JsonProcessingException {
        Object generateOrder = orderService.create(orderModel);
        return Result.success(generateOrder);
    }

    @ApiOperation("确认订单")
    @PostMapping("confirm")
    public ResultVo confirmOrder(@RequestBody @Validated OrderModel orderModel) {
        if (orderModel.getOrderId() == null) {
            return Result.fail();
        }
        boolean b = orderService.submit(orderModel);
        return Result.successFlag(b);
    }

    @ApiOperation("取消订单")
    @PostMapping("cancel")
    public ResultVo cancelOrder(@RequestBody @Validated OrderModel orderModel) {
        boolean b = orderService.cancelOrder(orderModel);
        return Result.successFlag(b);
    }

}
