package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (MbPost)实体类
 *
 * @author Violet
 * @since 2020-11-19 00:57:29
 */
@Data
@Accessors(chain = true)
@ToString
public class MbPost implements Serializable {
    private static final long serialVersionUID = 336793546031618309L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    private Long tid;
    /**
     * 关联的用户ID
     */
    @ApiModelProperty(notes = "关联的用户ID")
    private Long uid;
    /**
     * 记录这个帖子在哪个版块下发表的，默认是【杂谈】
     */
    @ApiModelProperty(notes = "记录这个帖子在哪个版块下发表的，默认是【杂谈】")
    private Long bid;
    /**
     * 帖子标题
     */
    @ApiModelProperty(notes = "帖子标题")
    private String title;
    /**
     * 帖子内容
     */
    @ApiModelProperty(notes = "帖子内容")
    private String content;
    /**
     * 帖子状态，10A有效，10B已删除
     */
    @ApiModelProperty(notes = "帖子状态，10A有效，10B已删除")
    private String postState;
    /**
     * 帖子发布日期
     */
    @ApiModelProperty(notes = "帖子发布日期")
    private Date createDate;
    /**
     * 帖子修改日期
     */
    @ApiModelProperty(notes = "帖子修改日期")
    private Date updateDate;
    /**
     * 状态，记录该条状态是否有效,0有效，1无效
     */
    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;


}