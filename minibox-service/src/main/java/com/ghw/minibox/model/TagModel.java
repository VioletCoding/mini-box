package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Violet
 * @description 标签 实体
 * @date 2021/2/2
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_tag")
public class TagModel implements Serializable {
    private static final long serialVersionUID = -76639098783076019L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("关联的游戏ID")
    private Long gameId;

    @ApiModelProperty("状态，记录当前记录是否有效，1有效，0无效")
    private String state;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新时间")
    private Date updateDate;
}
