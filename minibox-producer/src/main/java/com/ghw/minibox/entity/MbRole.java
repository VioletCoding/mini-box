package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (MbRole)实体类
 *
 * @author Violet
 * @since 2020-11-19 00:57:48
 */
@Data
@Accessors(chain = true)
@ToString
public class MbRole implements Serializable {
    private static final long serialVersionUID = 606866832195090120L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    private Long rid;
    /**
     * 角色名称，枚举值USER | ADMIN
     */
    @ApiModelProperty(notes = "角色名称，枚举值USER | ADMIN")
    private String name;
    /**
     * 状态，记录当前记录是否有效，0有效，1无效
     */
    @ApiModelProperty(notes = "状态，记录当前记录是否有效，0有效，1无效")
    private Integer state;


}