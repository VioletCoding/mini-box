package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (MbComment)实体类
 *
 * @author Violet
 * @since 2020-11-19 00:56:50
 */
@Data
@Accessors(chain = true)
@ToString
public class MbComment implements Serializable {
    private static final long serialVersionUID = 570346955477839827L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
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
    private String content;
    /**
     * 评论类型，TC为正常帖子下的评论，RC是回复其他用户的评论，GC是游戏下的评论
     */
    @ApiModelProperty(notes = "评论类型，TC为正常帖子下的评论，RC是回复其他用户的评论，GC是游戏下的评论")
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


}