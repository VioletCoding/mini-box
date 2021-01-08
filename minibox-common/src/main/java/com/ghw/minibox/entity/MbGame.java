package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * (MbGame)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:11
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "mb_game", replicas = 0)
public class MbGame implements Serializable {
    private static final long serialVersionUID = -20810077618725182L;
    @Id
    @ApiModelProperty(notes = "主键，自增，默认从10000开始自增，建表时候指定，唯一标识 ")
    @NotNull(message = "游戏id不能为空")
    private Long id;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    @ApiModelProperty(notes = "游戏中文名")
    @NotEmpty(message = "游戏中文名name不能为空")
    private String name;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "游戏现价格，保留两位小数，注意默认是0.00，否则会在加减乘除上带来转换问题")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private BigDecimal price;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(notes = "记录状态，0有效，1无效")
    private Integer state;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(notes = "游戏状态,0有效，1无效，如游戏下架等。")
    private Integer gameState;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "游戏评分，最大10分，保留一位小数，算法为所有评分总和除以评分人数=评分")
    private Double score;

    @Field(type = FieldType.Text)
    @ApiModelProperty(notes = "游戏简介")
    @NotEmpty(message = "游戏简介description不能为空")
    private String description;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "游戏原价")
    private Double originPrice;

    @Field(type = FieldType.Text)
    @ApiModelProperty(notes = "游戏封面图")
    private String coverImg;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date releaseTime;

    @Field(type = FieldType.Text)
    @ApiModelProperty(notes = "开发商")
    @NotEmpty(message = "游戏开发商developer不能为空")
    private String developer;

    @Field(type = FieldType.Text)
    @ApiModelProperty(notes = "发行商")
    @NotEmpty(message = "游戏发行商publisher不能为空")
    private String publisher;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(notes = "评分人数")
    private Integer scoreCount;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateDate;

    @Field(type = FieldType.Nested)
    @ApiModelProperty(notes = "一个游戏对应一个版块")
    private MbBlock mbBlock;

    @Field(type = FieldType.Nested)
    @ApiModelProperty(notes = "游戏详情多图")
    private List<MbPhoto> photoList;

    @Field(type = FieldType.Nested)
    @ApiModelProperty(notes = "一个游戏多标签")
    private List<MbTag> tagList;

    @Field(type = FieldType.Nested)
    @ApiModelProperty(notes = "一个游戏多评论")
    private List<MbComment> commentList;
}