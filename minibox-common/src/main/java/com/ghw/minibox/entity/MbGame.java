package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
public class MbGame implements Serializable {
    private static final long serialVersionUID = -20810077618725182L;
    /**
     * 主键，自增，默认从10000开始自增，建表时候指定，唯一标识
     */
    @ApiModelProperty(notes = "主键，自增，默认从10000开始自增，建表时候指定，唯一标识 ")
    @NotNull(message = "游戏gid不能为空")
    private Long gid;
    /**
     * 游戏中文名
     */
    @ApiModelProperty(notes = "游戏中文名")
    @NotNull(message = "游戏中文名name不能为空")
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
    @NotNull(message = "游戏简介description不能为空")
    private String description;
    /**
     * 发布时间
     */
    @ApiModelProperty(notes = "发布时间")
    @NotNull(message = "发布时间releaseTime不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;
    /**
     * 开发商
     */
    @ApiModelProperty(notes = "开发商")
    @NotNull(message = "游戏开发商developer不能为空")
    private String developer;
    /**
     * 发行商
     */
    @ApiModelProperty(notes = "发行商")
    @NotNull(message = "游戏发行商publisher不能为空")
    private String publisher;
    /**
     * 评分人数
     */
    @ApiModelProperty(notes = "评分人数")
    private Integer scoreCount;
    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    /**
     * 更新时间
     */
    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(notes = "一个游戏对应一个版块")
    private MbBlock mbBlock;

    @ApiModelProperty(notes = "一个游戏多图")
    private List<MbPhoto> photoList;

    @ApiModelProperty(notes = "用户头像")
    private MbPhoto userPhoto;

    @ApiModelProperty(notes = "一个游戏多标签")
    private List<MbTag> tagList;

    @ApiModelProperty(notes = "一个游戏多用户可以拥有")
    private List<MbUser> userList;

    @ApiModelProperty(notes = "一个游戏多评论")
    private List<MbComment> commentList;
}