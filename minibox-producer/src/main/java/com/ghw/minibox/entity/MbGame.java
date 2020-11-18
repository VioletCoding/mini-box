package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (MbGame)实体类
 *
 * @author Violet
 * @since 2020-11-19 00:45:12
 */
@Data
@Accessors(chain = true)
@ToString
public class MbGame implements Serializable {
    private static final long serialVersionUID = 245277053309255228L;
    /**
     * 主键，自增，默认从10000开始自增，建表时候指定，唯一标识
     */
    @ApiModelProperty(notes = "主键，自增，默认从10000开始自增，建表时候指定，唯一标识 ")
    private Long gid;
    /**
     * 游戏中文名
     */
    @ApiModelProperty(notes = "游戏中文名")
    private String name;
    /**
     * 游戏价格，保留两位小数，注意默认是0.00，否则会在加减乘除上带来转换问题
     */
    @ApiModelProperty(notes = "游戏价格，保留两位小数，注意默认是0.00，否则会在加减乘除上带来转换问题")
    private Double price;
    /**
     * 记录状态，0有效，1无效
     */
    @ApiModelProperty(notes = "记录状态，0有效，1无效")
    private Integer state;
    /**
     * 游戏状态,0有效，1无效，如游戏下架等。
     */
    @ApiModelProperty(notes = "游戏状态,0有效，1无效，如游戏下架等。")
    private Integer gameState;
    /**
     * 游戏评分，最大5分，保留一位小数，算法为所有评分总和除以评分人数=评分
     */
    @ApiModelProperty(notes = "游戏评分，最大5分，保留一位小数，算法为所有评分总和除以评分人数=评分")
    private Double score;
    /**
     * 游戏简介
     */
    @ApiModelProperty(notes = "游戏简介")
    private String description;
    /**
     * 发布时间
     */
    @ApiModelProperty(notes = "发布时间")
    private Date releaseTime;
    /**
     * 开发商
     */
    @ApiModelProperty(notes = "开发商")
    private String developer;
    /**
     * 发行商
     */
    @ApiModelProperty(notes = "发行商")
    private String publisher;
    /**
     * 评分人数
     */
    @ApiModelProperty(notes = "评分人数")
    private Integer scoreCount;


}