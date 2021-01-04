package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (MbTag)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:19
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbTag implements Serializable {
    private static final long serialVersionUID = -76639098783076019L;

    @ApiModelProperty(notes = "主键")
    private Long id;

    @ApiModelProperty(notes = "标签名称")
    private String tagName;

    @ApiModelProperty(notes = "关联的游戏ID")
    private Long gid;

    @ApiModelProperty(notes = "状态，记录当前记录是否有效，0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "创建时间")
    private Date createDate;

    @ApiModelProperty(notes = "更新时间")
    private Date updateDate;


}