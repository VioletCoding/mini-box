package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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

    @ApiModelProperty(notes = "主键")
    private Long tid;

    @ApiModelProperty(notes = "关联的用户ID")
    private Long uid;

    @ApiModelProperty(notes = "记录这个帖子在哪个版块下发表的，默认是【杂谈】")
    private Long bid;

    @ApiModelProperty(notes = "帖子标题")
    @NotEmpty(message = "帖子标题title不能为空")
    private String title;

    @ApiModelProperty(notes = "帖子内容")
    @NotEmpty(message = "帖子内容content不能为空")
    private String content;

    @ApiModelProperty(notes = "帖子状态，0有效，1删除")
    private String postState;

    @ApiModelProperty(notes = "帖子封面图")
    @NotEmpty(message = "帖子封面图不能为空")
    private String coverImg;

    @ApiModelProperty(notes = "首页评论数,这个字段不存数据库，只是为了方便首页列表展示评论数")
    private Long countComment;

    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "一篇帖子多条评论")
    private List<MbComment> commentList;

    @ApiModelProperty(notes = "作者头像")
    private MbPhoto mbPhoto;

    @ApiModelProperty(notes = "一个帖子对应一个版块")
    private MbBlock mbBlock;

    @ApiModelProperty(notes = "一个帖子对应一个用户，该用户是发帖人")
    private MbUser mbUser;

}