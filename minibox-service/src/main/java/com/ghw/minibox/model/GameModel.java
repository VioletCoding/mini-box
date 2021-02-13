package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Violet
 * @description 游戏 实体
 * @date 2021/2/2
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_game")
@Document(indexName = "game")
public class GameModel implements Serializable {
    private static final long serialVersionUID = -20810077618725182L;
    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("游戏中文名")
    @NotBlank(message = "游戏名称不能为空")
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;

    @ApiModelProperty("游戏现价格，保留两位小数，注意默认是0.00，否则会在加减乘除上带来转换问题")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    @NotNull(message = "游戏现价不能为空")
    @Field(type = FieldType.Keyword)
    private BigDecimal price;

    @ApiModelProperty("记录状态，1有效，0无效")
    @Field(type = FieldType.Keyword)
    private String state;

    @ApiModelProperty("游戏评分，最大10分，保留一位小数，算法为所有评分总和除以评分人数=评分")
    @Field(type = FieldType.Keyword)
    private Double score;

    @ApiModelProperty("游戏简介")
    @NotBlank(message = "游戏简介description不能为空")
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String description;

    @ApiModelProperty("游戏原价")
    @NotNull(message = "游戏原价不能为空")
    @Field(type = FieldType.Keyword)
    private Double originPrice;

    @ApiModelProperty("游戏封面图")
    @NotBlank(message = "游戏封面图不能为空")
    @Field(type = FieldType.Keyword)
    private String photoLink;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "游戏发布时间不能为空")
    @Field(type = FieldType.Keyword)
    private Date releaseTime;

    @ApiModelProperty("开发商")
    @NotBlank(message = "游戏开发商developer不能为空")
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String developer;

    @ApiModelProperty("发行商")
    @NotBlank(message = "游戏发行商publisher不能为空")
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String publisher;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Keyword)
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Keyword)
    private Date updateDate;
}
