package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * (MbPhoto)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:13
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbPhoto implements Serializable {
    private static final long serialVersionUID = -65749688597497972L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "图片pid不能为空")
    private Long pid;
    /**
     * 图片类型，枚举，用户头像UP、帖子图片TP、评论图片CP、游戏图片GP
     */
    @ApiModelProperty(notes = "图片类型，枚举，用户头像UP、帖子图片TP、评论图片CP、游戏图片GP")
    @NotNull(message = "图片type不能为空，必须是PostType里定义的枚举值")
    private String type;
    /**
     * 图片链接
     */
    @ApiModelProperty(notes = "图片链接")
    @NotNull(message = "图片链接link不能为空")
    private String link;
    /**
     * 如果type=UP，该字段必填
     */
    @ApiModelProperty(notes = "如果type=UP，该字段必填")
    private Long uid;
    /**
     * 如果type=TP，该字段必填
     */
    @ApiModelProperty(notes = "如果type=TP，该字段必填")
    private Long tid;
    /**
     * 如果type=CP，该字段必填
     */
    @ApiModelProperty(notes = "如果type=CP，该字段必填")
    private Long cid;
    /**
     * 如果type=GP，该字段必填
     */
    @ApiModelProperty(notes = "如果type=GP，该字段必填")
    private Long gid;
    /**
     * 状态，记录该条状态是否有效,0有效，1无效
     */
    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;
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

    @ApiModelProperty(notes = "一张图片对应一个用户头像")
    private MbUser mbUser;

}