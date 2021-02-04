package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Violet
 * @description 版块实体
 * @date 2021/2/1
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_block")
public class BlockModel implements Serializable {
    private static final long serialVersionUID = 820635847901764243L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("版块（社区）名称")
    @NotBlank(message = "版块名称不能为空")
    private String name;

    @NotBlank(message = "版块图片不能为空")
    private String photoLink;

    @ApiModelProperty("关联的游戏id")
    @NotNull(message = "版块关联的游戏不能为空")
    private Long gameId;

    @ApiModelProperty("记录状态，0有效，1无效")
    private String state;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
