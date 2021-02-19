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
 * @description 游戏 一对多 图片 实体
 * @date 2021/2/2
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_photo")
public class PhotoModel implements Serializable {
    private static final long serialVersionUID = -65749688597497972L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("图片链接")
    @NotBlank(message = "图片链接photoLink不能为空")
    private String photoLink;

    @ApiModelProperty("游戏id")
    @NotNull(message = "游戏id不可为空")
    private Long gameId;

    @ApiModelProperty("状态，记录该条状态是否有效,1有效，0无效")
    private String state;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateDate;
}
