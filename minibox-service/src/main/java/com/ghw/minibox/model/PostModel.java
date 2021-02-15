package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Violet
 * @description 帖子实体
 * @date 2021/2/1
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_post")
public class PostModel implements Serializable {
    private static final long serialVersionUID = -34132088850314000L;
    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("作者ID")
    @NotNull(message = "作者ID不能为空")
    private Long authorId;

    @ApiModelProperty("版块ID")
    @NotNull(message = "版块ID不能为空")
    private Long blockId;

    @ApiModelProperty("帖子标题")
    @NotBlank(message = "帖子标题不能为空")
    private String title;

    @ApiModelProperty("帖子内容")
    @NotBlank(message = "帖子内容不能为空")
    private String content;

    @ApiModelProperty("帖子封面图")
    @NotBlank(message = "帖子封面图不能为空")
    private String photoLink;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty("状态，记录该条状态是否有效,1有效，0无效")
    private String state;
}
