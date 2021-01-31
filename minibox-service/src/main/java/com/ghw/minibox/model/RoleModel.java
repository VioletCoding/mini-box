package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @author Violet
 * @description 角色实体
 * @date 2021/1/31
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_role")
public class RoleModel implements Serializable {
    private static final long serialVersionUID = 297485414723529586L;

    @ApiModelProperty(notes = "主键")
    @NotNull(message = "角色id不能为空")
    private Long id;

    @ApiModelProperty(notes = "角色名称，枚举值USER | ADMIN")
    @NotEmpty(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(notes = "状态，记录当前记录是否有效，0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
}
