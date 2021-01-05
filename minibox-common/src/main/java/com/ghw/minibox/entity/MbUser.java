package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghw.minibox.validatedgroup.LoginGroup;
import com.ghw.minibox.validatedgroup.SingleGroup;
import com.ghw.minibox.validatedgroup.UpdatePassword;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (MbUser)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:21
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbUser implements Serializable {
    private static final long serialVersionUID = -71315955846878471L;

    @ApiModelProperty(notes = "主键")
    @NotNull(message = "用户id不能为空", groups = UpdatePassword.class)
    private Long id;

    @ApiModelProperty("用户头像")
    @NotEmpty(message = "用户头像不能为空")
    private String userImg;

    @ApiModelProperty(notes = "昵称")
    @NotEmpty(message = "用户nickname不能为空")
    @Size(max = 12)
    private String nickname;

    @ApiModelProperty(notes = "用户名，本系统是邮箱，需要程序校验")
    @Email(message = "邮箱格式不正确", groups = {LoginGroup.class, SingleGroup.class})
    @NotEmpty(message = "用户username不能为空", groups = {LoginGroup.class, SingleGroup.class})
    @Size(max = 50, groups = {LoginGroup.class, SingleGroup.class})
    private String username;

    @ApiModelProperty(notes = "密码，MD5加密")
    @NotEmpty(message = "密码不能为空", groups = {LoginGroup.class, UpdatePassword.class})
    @Size(min = 8, max = 16, groups = {LoginGroup.class, UpdatePassword.class})
    private String password;

    @ApiModelProperty(notes = "个人简介")
    @Size(max = 40)
    private String description;

    @ApiModelProperty(notes = "个人等级")
    private String level;

    @ApiModelProperty(notes = "用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）")
    private String userState;

    @ApiModelProperty(notes = "用户创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(notes = "字段更新时间，修改该条记录则自动更新这个字段")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty(notes = "状态，记录当前记录是否有效，0有效，1无效")
    private Integer state;

    @ApiModelProperty(notes = "一个用户多篇帖子")
    private List<MbPost> postList;

    @ApiModelProperty(notes = "一个用户多条评论")
    private List<MbComment> commentList;

    @ApiModelProperty(notes = "一个用户多个游戏")
    private List<MbGame> gameList;
}