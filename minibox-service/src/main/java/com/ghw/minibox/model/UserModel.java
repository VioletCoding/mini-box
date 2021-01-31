package com.ghw.minibox.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

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

    @ApiModelProperty(notes = "主键")
    private Long id;

    @ApiModelProperty("用户头像")
    private String photoLink;

    @ApiModelProperty(notes = "昵称")
    private String nickname;

    @ApiModelProperty(notes = "用户名，本系统是邮箱，需要程序校验")
    @Email(message = "邮箱格式不正确")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(notes = "密码，MD5加密")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 16)
    private String password;

    @ApiModelProperty(notes = "个人简介")
    private String description;

    @ApiModelProperty(notes = "用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）")
    private String userState;

    @ApiModelProperty(notes = "用户创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "字段更新时间，修改该条记录则自动更新这个字段")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(notes = "状态，记录当前记录是否有效，0有效，1无效")
    private String state;
}
