package com.ghw.minibox.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Violet
 * @description
 * @date 2021/1/26
 */
@Data
@Document(indexName = "game_model")
public class ESGameModel implements Serializable {
    private static final long serialVersionUID = -208100776325182L;

    @ApiModelProperty("主键")
    @NotNull(message = "游戏id不能为空")
    private Long id;

    @ApiModelProperty("游戏中文名")
    @NotBlank(message = "游戏中文名不能为空")
    private String name;

    @ApiModelProperty("游戏现价格，保留两位小数，注意默认是0.00，否则会在加减乘除上带来转换问题")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private BigDecimal price;

    @ApiModelProperty("记录状态，1有效，0无效")
    private String state;

    @ApiModelProperty("游戏评分，最大10分，保留一位小数，算法为所有评分总和除以评分人数=评分")
    private BigDecimal score;

    @ApiModelProperty("游戏简介")
    @NotBlank(message = "游戏简介description不能为空")
    private String description;

    @ApiModelProperty("游戏原价")
    private Double originPrice;

    @ApiModelProperty("游戏封面图")
    private String photoLink;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;

    @ApiModelProperty("开发商")
    @NotEmpty(message = "游戏开发商developer不能为空")
    private String developer;

    @ApiModelProperty("发行商")
    @NotEmpty(message = "游戏发行商publisher不能为空")
    private String publisher;

    @ApiModelProperty("评分人数")
    private Integer scoreCount;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateDate;
}
