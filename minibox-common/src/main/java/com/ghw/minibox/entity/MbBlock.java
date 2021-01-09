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
import java.util.List;

/**
 * (MbBlock)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:08
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbBlock implements Serializable {
    private static final long serialVersionUID = 820635847901764243L;

    @ApiModelProperty(notes = "主键")
    @NotNull(message = "版块id不能为空")
    private Long id;

    @ApiModelProperty(notes = "版块（社区）名称")
    @NotEmpty(message = "版块名称name不能为空")
    private String name;

    @ApiModelProperty(notes = "关联的游戏id")
    @NotNull(message = "版块关联的游戏gid不能为空")
    private Long gid;

    @ApiModelProperty(notes = "记录状态，0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(notes = "一个版块对应一个游戏，否则就是杂谈")
    private MbGame mbGame;

    @ApiModelProperty(notes = "一个版块下多篇帖子")
    private List<MbPost> postList;
}