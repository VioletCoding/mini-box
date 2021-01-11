package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Violet
 * @description 角色-用户 关系表
 * @date 2021/1/10
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbRoleUser {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("角色id")
    private Long rid;

    @ApiModelProperty("记录状态，0有效，1无效")
    private Integer state;

    @ApiModelProperty("记录生成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("记录更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
}
