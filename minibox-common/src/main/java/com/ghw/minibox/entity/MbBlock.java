package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "版块bid不能为空")
    private Long bid;
    /**
     * 版块名称，一般是【游戏名称】，如果不是【游戏名称】，就是【杂谈】
     */
    @ApiModelProperty(notes = "版块名称，一般是【游戏名称】，如果不是【游戏名称】，就是【杂谈】")
    @NotNull(message = "版块名称name不能为空")
    private String name;
    /**
     * 关联的游戏id
     */
    @ApiModelProperty(notes = "关联的游戏id")
    @NotNull(message = "版块关联的游戏gid不能为空")
    private Long gid;
    /**
     * 记录状态，0有效，1无效
     */
    @ApiModelProperty(notes = "记录状态，0有效，1无效")
    private Integer state;
    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;

    @ApiModelProperty(notes = "一个版块对应一个游戏，否则就是杂谈")
    private MbGame mbGame;

    @ApiModelProperty(notes = "一个版块下多篇帖子")
    private List<MbPost> postList;
}