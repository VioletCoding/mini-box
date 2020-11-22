package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghw.minibox.validatedgroup.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
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
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "用户uid不能为空", groups = {IsUserLegal.class, SearchGroup.class, UpdateUserInfoGroup.class})
    private Long uid;
    /**
     * 昵称
     */
    @ApiModelProperty(notes = "昵称")
    @NotNull(message = "用户nickname不能为空", groups = {UpdateUserInfoGroup.class})
    @Size(max = 12)
    private String nickname;
    /**
     * 用户名，本系统是邮箱，需要程序校验
     */
    @ApiModelProperty(notes = "用户名，本系统是邮箱，需要程序校验")
    @NotNull(message = "用户username不能为空", groups = {LoginGroup.class, RegisterGroup.class})
    @Email(message = "邮箱格式不正确")
    @Size(max = 50)
    private String username;
    /**
     * 密码，MD5加密
     */
    @ApiModelProperty(notes = "密码，MD5加密")
    @NotNull(message = "密码不能为空", groups = {LoginGroup.class, RegisterGroup.class})
    @Size(min = 8, max = 16)
    private String password;
    /**
     * 个人简介
     */
    @ApiModelProperty(notes = "个人简介")
    @Size(max = 40, groups = {UpdateUserInfoGroup.class})
    private String description;
    /**
     * 个人等级
     */
    @ApiModelProperty(notes = "个人等级")
    private String level;
    /**
     * 钱包余额
     */
    @ApiModelProperty(notes = "钱包余额")
    private Double walletBalance;
    /**
     * 用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）
     */
    @ApiModelProperty(notes = "用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）")
    private String userState;
    /**
     * 用户创建日期
     */
    @ApiModelProperty(notes = "用户创建日期")
    private Date createDate;
    /**
     * 经验，每次升级需要10经验，升级后该字段值update为0
     */
    @ApiModelProperty(notes = "经验，每次升级需要10经验，升级后该字段值update为0")
    @Size(max = 10)
    private Integer exp;
    /**
     * 字段更新时间，修改该条记录则自动更新这个字段
     */
    @ApiModelProperty(notes = "字段更新时间，修改该条记录则自动更新这个字段")
    private Date updateDate;
    /**
     * 状态，记录当前记录是否有效，0有效，1无效
     */
    @ApiModelProperty(notes = "状态，记录当前记录是否有效，0有效，1无效")
    @Size(max = 1)
    private Integer state;

    @ApiModelProperty(notes = "一个用户多篇帖子")
    private List<MbPost> postList;

    @ApiModelProperty(notes = "一个用户多条评论")
    private List<MbComment> commentList;

    @ApiModelProperty(notes = "一个用户多个游戏")
    private List<MbGame> gameList;
}