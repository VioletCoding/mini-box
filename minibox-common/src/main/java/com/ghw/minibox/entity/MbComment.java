package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghw.minibox.validatedgroup.CommentInGameGroup;
import com.ghw.minibox.validatedgroup.CommentInPostGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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

    @ApiModelProperty(notes = "主键")
    @NotNull(message = "评论cid不能为空")
    private Long cid;

    @ApiModelProperty(notes = "记录状态，0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "评论内容")
    @NotEmpty(message = "评论内容content不能为空",groups = {CommentInPostGroup.class, CommentInGameGroup.class})
    private String content;

    @ApiModelProperty(notes = "评论类型，TC为正常帖子下的评论，RC是回复其他用户的评论，GC是游戏下的评论")
    @NotEmpty(message = "评论类型type不能为空", groups = {CommentInPostGroup.class, CommentInGameGroup.class})
    private String type;

    @ApiModelProperty(notes = "帖子ID，如果type=TC，该字段必填")
    @NotNull(message = "评论tid不能为空")
    private Long tid;

    @ApiModelProperty(notes = "用户ID，如果type=RC，该字段必填")
    @NotNull(message = "用户uid不能为空", groups = {CommentInPostGroup.class, CommentInGameGroup.class})
    private Long uid;

    @ApiModelProperty(notes = "游戏ID，如果type=GC，该字段必填")
    private Long gid;

    @ApiModelProperty(notes = "用户对游戏的评分，如果type=GC，该字段必填")
    private Double score;

    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "更新时间")
    private Date updateDate;

    @ApiModelProperty(notes = "一个评论对应一个用户")
    private MbUser mbUser;

    @ApiModelProperty(notes = "一个评论可以带一张图片")
    private MbPhoto mbPhoto;

    @ApiModelProperty(notes = "一个评论有多个回复")
    private List<MbReply> replyList;

}