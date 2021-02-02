package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Violet
 * @description 订单实体
 * @date 2021/2/2
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_order")
public class OrderModel {
    private static final long serialVersionUID = 623067205908153350L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty("订单号")
    private Long orderId;

    @ApiModelProperty("游戏id")
    @NotNull(message = "游戏id不能为空")
    private Long gameId;

    @ApiModelProperty("交易金额")
    @NotNull(message = "交易金额不能为空")
    private BigDecimal orderCost;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "订单更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(notes = "数据状态，0可用，1禁用")
    private String state;

    @ApiModelProperty("订单是否交易成功")
    private String successFlag;
}
