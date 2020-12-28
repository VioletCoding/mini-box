package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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

    @ApiModelProperty(notes = "主键")
    @NotNull(message = "图片pid不能为空")
    private Long pid;

    @ApiModelProperty(notes = "图片类型，枚举，用户头像UP、帖子图片TP、评论图片CP、游戏图片GP")
    @NotEmpty(message = "图片type不能为空，必须是PostType里定义的枚举值")
    private String type;

    @ApiModelProperty(notes = "图片链接")
    @NotEmpty(message = "图片链接link不能为空")
    private String photoLink;

    @ApiModelProperty(notes = "如果type=UP，该字段必填")
    private Long uid;

    @ApiModelProperty(notes = "如果type=TP，该字段必填")
    private Long tid;

    @ApiModelProperty(notes = "如果type=CP，该字段必填")
    private Long cid;

    @ApiModelProperty(notes = "如果type=GP，该字段必填")
    private Long gid;

    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "创建时间")
    private Date createDate;

    @ApiModelProperty(notes = "更新时间")
    private Date updateDate;

    @ApiModelProperty(notes = "一张图片对应一个用户头像")
    private MbUser mbUser;

}