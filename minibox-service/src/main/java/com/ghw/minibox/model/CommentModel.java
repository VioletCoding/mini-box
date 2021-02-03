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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Violet
 * @description 评论实体
 * @date 2021/2/2
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_comment")
public class CommentModel implements Serializable {
    private static final long serialVersionUID = -88043199982765063L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("记录状态，1有效，0无效")
    private String state;

    @ApiModelProperty("评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty("帖子ID")
    private Long postId;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty("游戏ID")
    private Long gameId;

    @ApiModelProperty("用户对游戏的评分")
    private BigDecimal score;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @TableField(exist = false)
    private UserModel userModel;

    @TableField(exist = false)
    private PostModel postModel;

    @TableField(exist = false)
    private GameModel gameModel;

    @TableField(exist = false)
    private List<ReplyModel> replyModel;


}
