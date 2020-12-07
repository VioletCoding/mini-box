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
 * (MbComment)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:10
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbComment implements Serializable {
    private static final long serialVersionUID = -88043199982765063L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "评论cid不能为空")
    private Long cid;
    /**
     * 记录状态，0有效，1无效
     */
    @ApiModelProperty(notes = "记录状态，0有效，1无效")
    private Integer state;
    /**
     * 评论内容
     */
    @ApiModelProperty(notes = "评论内容")
    @NotNull(message = "评论内容content不能为空")
    private String content;
    /**
     * 评论类型，TC为正常帖子下的评论，RC是回复其他用户的评论，GC是游戏下的评论
     */
    @ApiModelProperty(notes = "评论类型，TC为正常帖子下的评论，RC是回复其他用户的评论，GC是游戏下的评论")
    @NotNull(message = "评论类型type不能为空")
    private String type;
    /**
     * 帖子ID，如果type=TC，该字段必填
     */
    @ApiModelProperty(notes = "帖子ID，如果type=TC，该字段必填")
    private Long tid;
    /**
     * 帖子ID，如果type=RC，该字段必填
     */
    @ApiModelProperty(notes = "帖子ID，如果type=RC，该字段必填")
    private Long uid;
    /**
     * 帖子ID，如果type=GC，该字段必填
     */
    @ApiModelProperty(notes = "帖子ID，如果type=GC，该字段必填")
    private Long gid;
    /**
     * 用户对游戏的评分，如果type=GC，该字段必填
     */
    @ApiModelProperty(notes = "用户对游戏的评分，如果type=GC，该字段必填")
    private Double score;
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
    private Date updateDate;

    @ApiModelProperty(notes = "一个评论对应一个用户")
    private MbUser mbUser;

    @ApiModelProperty(notes = "一个评论可以带一张图片")
    private MbPhoto mbPhoto;

    @ApiModelProperty(notes = "一个评论有多个回复")
    private List<MbComment> commentList;

}