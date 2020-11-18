package com.ghw.minibox.entity;

import com.ghw.minibox.validatedgroup.LoginGroup;
import com.ghw.minibox.validatedgroup.RegisterGroup;
import com.ghw.minibox.validatedgroup.SearchGroup;
import com.ghw.minibox.validatedgroup.UpdateUserInfoGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * (MbUser)实体类
 *
 * @author Violet
 * @since 2020-11-18 23:34:56
 */
@Data
@Accessors(chain = true)
@ToString
public class MbUser implements Serializable {
    private static final long serialVersionUID = 448352555695927206L;
    /**
     * 主键
     */
    @ApiModelProperty(notes = "主键")
    @NotNull(message = "用户uid不能为空", groups = {SearchGroup.class})
    private Long uid;
    /**
     * 昵称
     */
    @ApiModelProperty(notes = "昵称")
    @NotNull(message = "用户昵称nickname不能为空", groups = {UpdateUserInfoGroup.class})
    private String nickname;
    /**
     * 用户名，本系统是邮箱，需要程序校验
     */
    @NotNull(message = "用户名username不能为空", groups = {LoginGroup.class, RegisterGroup.class})
    @Email(message = "用户名必须是email格式")
    @Size(min = 8, max = 100)
    @ApiModelProperty(notes = "用户名")
    private String username;
    /**
     * 密码，MD5加密
     */
    @NotNull(message = "密码password不能为空", groups = {LoginGroup.class, RegisterGroup.class})
    @Size(min = 8, max = 16)
    @ApiModelProperty(notes = "密码")
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
    @ApiModelProperty(notes = "用户状态")
    private String userState;
    /**
     * 用户创建日期
     */
    @ApiModelProperty(notes = "用户创建日期")
    private Date createDate;
    /**
     * 经验，每次升级需要10经验，升级后该字段值update为0
     */
    @ApiModelProperty(notes = "经验")
    private Integer exp;
    /**
     * 字段更新时间，修改该条记录则自动更新这个字段
     */
    @ApiModelProperty(notes = "字段更新时间")
    private Date updateDate;
    /**
     * 状态，记录当前记录是否有效，0有效，1无效
     */
    @ApiModelProperty(notes = "状态")
    private Integer state;

}