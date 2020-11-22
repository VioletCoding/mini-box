package com.ghw.minibox.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (MbRole)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:17
 */
@Data
@Accessors(chain = true)
public class MbRole implements Serializable {
    private static final long serialVersionUID = 297485414723529586L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "角色rid不能为空")
    private Long rid;
    /**
     * 角色名称，枚举值USER | ADMIN
     */
    @ApiModelProperty(notes = "角色名称，枚举值USER | ADMIN")
    @NotNull(message = "角色名称name不能为空，必须是UserRole里定义的枚举值")
    private String name;
    /**
     * 状态，记录当前记录是否有效，0有效，1无效
     */
    @ApiModelProperty(notes = "状态，记录当前记录是否有效，0有效，1无效")
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

    @ApiModelProperty(notes = "一个角色多个用户")
    private List<MbUser> userList;

}