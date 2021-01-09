package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbOrder;
import com.ghw.minibox.service.impl.OrderImpl;
import com.ghw.minibox.utils.ResultCode;
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
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderImpl order;
    @Resource
    private GenerateResult<Object> gr;

    @ApiOperation("生成订单")
    @PostMapping("generateOrder")
    public ReturnDto<Object> generateOrder(@RequestBody @Validated MbOrder mbOrder) throws JsonProcessingException {

        Object generateOrder = order.insert(mbOrder);

        if (generateOrder != null) return gr.success(generateOrder);

        return gr.fail(ResultCode.ORDER_GENERATE_FAIL);
    }

    @ApiOperation("确认订单")
    @PostMapping("confirm")
    public ReturnDto<Object> confirmOrder(@RequestBody @Validated MbOrder mbOrder) {

        boolean b = order.update(mbOrder);

        if (b) return gr.success();

        return gr.fail(ResultCode.ORDER_CANCEL);
    }

    @ApiOperation("取消订单")
    @PostMapping("cancel")
    public ReturnDto<Object> cancelOrder(@RequestBody @Validated MbOrder mbOrder) {

        boolean b = order.cancelOrder(mbOrder);

        if (b) return gr.success();

        return gr.fail(ResultCode.ORDER_CANCEL);
    }

}
