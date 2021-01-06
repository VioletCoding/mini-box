package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @NotNull(message = "图片id不能为空")
    private Long id;

    @ApiModelProperty(notes = "图片链接")
    @NotEmpty(message = "图片链接link不能为空")
    private String photoLink;

    @ApiModelProperty(notes = "游戏id")
    private Long gid;

    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(notes = "一张图片对应一个用户头像")
    private MbUser mbUser;

}