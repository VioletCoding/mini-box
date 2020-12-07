package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghw.minibox.validatedgroup.AuthGroup;
import com.ghw.minibox.validatedgroup.LoginGroup;
import com.ghw.minibox.validatedgroup.RegisterGroup;
import com.ghw.minibox.validatedgroup.SingleGroup;
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
    @NotNull(message = "用户uid不能为空")
    private Long uid;

    @ApiModelProperty(notes = "验证码")
    @NotNull(message = "验证码code不能为空", groups = {AuthGroup.class})
    private String code;
    /**
     * 昵称
     */
    @ApiModelProperty(notes = "昵称")
    @NotNull(message = "用户nickname不能为空")
    @Size(max = 12)
    private String nickname;
    /**
     * 用户名，本系统是邮箱，需要程序校验
     */
    @ApiModelProperty(notes = "用户名，本系统是邮箱，需要程序校验")
    @Email(message = "邮箱格式不正确", groups = {LoginGroup.class, RegisterGroup.class, SingleGroup.class})
    @NotNull(message = "用户username不能为空", groups = {LoginGroup.class, RegisterGroup.class, SingleGroup.class})
    @Size(max = 50, groups = {LoginGroup.class, RegisterGroup.class, SingleGroup.class})
    private String username;
    /**
     * 密码，MD5加密
     */
    @ApiModelProperty(notes = "密码，MD5加密")
    @NotNull(message = "密码不能为空", groups = {LoginGroup.class, RegisterGroup.class})
    @Size(min = 8, max = 16, groups = {LoginGroup.class, RegisterGroup.class})
    private String password;
    /**
     * 个人简介
     */
    @ApiModelProperty(notes = "个人简介")
    @Size(max = 40)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    /**
     * 经验，每次升级需要10经验，升级后该字段值update为0
     */
    @ApiModelProperty(notes = "经验，每次升级需要10经验，升级后该字段值update为0")
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
    private Integer state;

    @ApiModelProperty(notes = "一个用户可以有一张头像")
    private MbPhoto mbPhoto;

    @ApiModelProperty(notes = "一个用户多篇帖子")
    private List<MbPost> postList;

    @ApiModelProperty(notes = "一个用户多条评论")
    private List<MbComment> commentList;

    @ApiModelProperty(notes = "一个用户多个游戏")
    private List<MbGame> gameList;
}