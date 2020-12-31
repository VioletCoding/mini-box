package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (MbOrder)实体类
 *
 * @author Violet
 * @since 2020-12-31 15:43:24
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbOrder implements Serializable {
    private static final long serialVersionUID = 623067205908153350L;

    @ApiModelProperty(notes = "主键")
    private Long id;

    @ApiModelProperty(notes = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long uid;

    @ApiModelProperty(notes = "订单号")
    private Long orderId;

    @ApiModelProperty(notes = "游戏id")
    @NotNull(message = "游戏id不能为空")
    private Long orderGameId;

    @ApiModelProperty(notes = "交易金额")
    @NotNull(message = "交易金额不能为空")
    private BigDecimal orderCost;

    @ApiModelProperty(notes = "订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "订单更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(notes = "数据状态，0可用，1禁用")
    private Integer status;

    @ApiModelProperty(notes = "订单是否交易成功")
    private Integer success;

    @ApiModelProperty(notes = "订单包含的商品，本项目是游戏")
    private MbGame mbGame;

}