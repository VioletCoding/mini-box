package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Violet
 * @description 回复 实体
 * @date 2021/2/2
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_reply")
public class ReplyModel {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("回复谁,用户的id")
    @NotNull(message = "回复了谁,用户ID不能为空")
    private Long replyTargetId;

    @ApiModelProperty("回复内容")
    @NotBlank(message = "回复内容不能为空")
    private String content;

    @ApiModelProperty("回复时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty("发表这条回复的用户id")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty("在哪条评论下回复,评论id")
    @NotNull(message = "在哪条评论下的回复,评论ID")
    private Long commentId;

    @ApiModelProperty("记录状态")
    private String state;

    @TableField(exist = false)
    private UserModel userModel;
}
