package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Violet
 * @description 用户实体
 * @date 2021/1/31
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("mb_user")
public class UserModel implements Serializable {
    private static final long serialVersionUID = -71315955846878471L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户头像")
    private String photoLink;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户名")
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(max = 16)
    private String password;

    @ApiModelProperty("个人简介")
    private String description;

    @ApiModelProperty("用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）")
    private String userState;

    @ApiModelProperty("用户创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty("状态，记录当前记录是否有效，1有效，0无效")
    private String state;

    @TableField(exist = false)
    private List<GameModel> gameModelList;

    @TableField(exist = false)
    private List<RoleModel> roleModelList;
}
