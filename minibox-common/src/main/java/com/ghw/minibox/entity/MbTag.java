package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghw.minibox.validatedgroup.SearchGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "标签tid不能为空", groups = {SearchGroup.class})
    private Long tid;
    /**
     * 标签名称
     */
    @ApiModelProperty(notes = "标签名称")
    @NotNull(message = "标签tagName不能为空")
    @Size(max = 10, min = 1)
    private String tagName;
    /**
     * 关联的游戏ID
     */
    @ApiModelProperty(notes = "关联的游戏ID")
    private Long gid;
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


}