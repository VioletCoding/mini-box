package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbPost implements Serializable {
    private static final long serialVersionUID = -34132088850314000L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "帖子tid不能为空")
    private Long tid;
    /**
     * 关联的用户ID
     */
    @ApiModelProperty(notes = "关联的用户ID")
    @NotNull(message = "关联的用户uid不能为空")
    @Size(min = 5)
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
    @NotNull(message = "帖子标题title不能为空")
    private String title;
    /**
     * 帖子内容
     */
    @ApiModelProperty(notes = "帖子内容")
    @NotNull(message = "帖子内容content不能为空")
    private String content;
    /**
     * 帖子状态，0有效，1已删除
     */
    @ApiModelProperty(notes = "帖子状态，0有效，1删除")
    private String postState;

    /**
     * 这个字段不存数据库，目前临时解决方法是随便取一张做头图。。。。
     */
    @ApiModelProperty(notes = "帖子头图")
    private String headPhotoLink;

    /**
     * 这个字段不存数据库，只是为了方便首页列表展示评论数
     */
    @ApiModelProperty(notes = "首页评论数")
    private Integer countComment;
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
    /**
     * 状态，记录该条状态是否有效,0有效，1无效
     */
    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "一篇帖子多条评论")
    private List<MbComment> commentList;

    @ApiModelProperty(notes = "一篇帖子多张图片")
    private List<MbPhoto> photoList;

    @ApiModelProperty(notes = "一个帖子对应一个版块")
    private MbBlock mbBlock;

    @ApiModelProperty(notes = "一个帖子对应一个用户，该用户是发帖人")
    private MbUser mbUser;

}