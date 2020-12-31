package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbOrder;
import com.ghw.minibox.service.MbOrderService;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author Violet
 * @description
 * @date 2020/12/31
 */
@RestController
@RequestMapping("order")
public class MbOrderController {
    @Resource
    private MbOrderService orderService;
    @Resource
    private GenerateResult<Object> gr;

    @PostMapping("generateOrder")
    public ReturnDto<Object> generateOrder(@RequestBody @Validated MbOrder mbOrder) throws JsonProcessingException, ParseException {
        MbOrder generateOrder = orderService.generateOrder(mbOrder);
        if (generateOrder != null) {
            return gr.success(generateOrder);
        }
        return gr.fail(ResultCode.ORDER_GENERATE_FAIL);
    }

    @PostMapping("confirm")
    public ReturnDto<Object> confirmOrder(@RequestBody @Validated MbOrder mbOrder) {
        boolean b = orderService.saveOrder(mbOrder);
        if (b) {
            return gr.success();
        }
        return gr.fail(ResultCode.ORDER_CANCEL);
    }

    @PostMapping("cancel")
    public ReturnDto<Object> cancelOrder(@RequestBody @Validated MbOrder mbOrder) {
        boolean b = orderService.cancelOrder(mbOrder);
        if (b) {
            return gr.success();
        }
        return gr.fail(ResultCode.ORDER_CANCEL);
    }

}
