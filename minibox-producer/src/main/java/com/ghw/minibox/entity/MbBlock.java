package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (MbBlock)实体类
 *
 * @author Violet
 * @since 2020-11-19 00:57:02
 */
@Data
@Accessors(chain = true)
@ToString
public class MbBlock implements Serializable {
    private static final long serialVersionUID = -59303442220784839L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    private Long bid;
    /**
     * 版块名称，一般是【游戏名称】，如果不是【游戏名称】，就是【杂谈】
     */
    @ApiModelProperty(notes = "版块名称，一般是【游戏名称】，如果不是【游戏名称】，就是【杂谈】")
    private String name;
    /**
     * 关联的游戏id
     */
    @ApiModelProperty(notes = "关联的游戏id")
    private Long gid;
    /**
     * 记录状态，0有效，1无效
     */
    @ApiModelProperty(notes = "记录状态，0有效，1无效")
    private Integer state;


}