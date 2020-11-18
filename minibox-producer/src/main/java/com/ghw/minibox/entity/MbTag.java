package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (MbTag)实体类
 *
 * @author Violet
 * @since 2020-11-19 00:57:59
 */
@Data
@Accessors(chain = true)
@ToString
public class MbTag implements Serializable {
    private static final long serialVersionUID = -80360363705171027L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    private Long tid;
    /**
     * 标签名称
     */
    @ApiModelProperty(notes = "标签名称")
    private String tagName;
    /**
     * 关联的游戏ID
     */
    @ApiModelProperty(notes = "关联的游戏ID")
    private Long gid;


}