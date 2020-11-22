package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (MbPost)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 */
@Data
@Accessors(chain = true)
public class MbPost implements Serializable {
    private static final long serialVersionUID = -34132088850314000L;
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
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    private Date createDate;
    /**
     * 更新时间
     */
    @ApiModelProperty(notes = "更新时间")
    private Date updateDate;
    /**
     * 状态，记录该条状态是否有效,0有效，1无效
     */
    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "一篇帖子多条评论")
    private List<MbComment> commentList;

    @ApiModelProperty(notes = "一篇帖子多张图片")
    private List<MbPhoto> photoList;

}