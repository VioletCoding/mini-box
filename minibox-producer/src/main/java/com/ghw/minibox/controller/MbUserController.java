package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.validatedgroup.AuthGroup;
import com.ghw.minibox.validatedgroup.LoginGroup;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MbUser)表控制层
 *
 * @author Violet
 * @since 2020-11-19 12:20:22
 */
@RestController
@RequestMapping("user")
@Slf4j
@ApiOperation("用户控制层")
public class MbUserController {

    @Resource
    private MbUserService mbUserService;

    @Resource
    private GenerateResult<MbUser> gr;

    /**
     * 登陆前的校验
     * <p>
     * 调用queryByUsername方法来检查该用户是否已存在，以及判断验证码是否已发送
     * 如果已发送，那么需要5分钟后等验证码过期了，才能再次请求
     * <p>
     * queryByUsername方法通过后，调用发邮件方法sendEmail发送验证码，
     * 该方法是异步方法
     *
     * @param mbUser 实体
     * @return ReturnDto
     */
    @AOPLog("注册前校验")
    @ApiOperation("注册前校验")
    @PostMapping("beforeRegister")
    public ReturnDto<MbUser> beforeRegister(@Validated(AuthGroup.class) @RequestBody MbUser mbUser) throws EmailException {
        String query = mbUserService.queryByUsername(mbUser.getUsername());
        if (query.equals(ResultCode.HAS_BEEN_SENT.getMessage()))
            return gr.custom(ResultCode.BAD_REQUEST.getCode(), "验证码已发送，请5分钟后再试");
        if (query.equals(ResultCode.USER_EXIST.getMessage()))
            return gr.custom(ResultCode.BAD_REQUEST.getCode(), "用户已存在");
        mbUserService.sendEmail(mbUser.getUsername());
        return gr.custom(ResultCode.OK.getCode(), "验证码发送成功，请检查邮箱！");
    }

    /**
     * 校验完后的注册
     * 调用authRegCode方法来校验，验证码是否一致
     * 校验通过后调用register来进行真正的用户注册
     *
     * @param mbUser 实体
     * @return ReturnDto
     */
    @AOPLog("注册")
    @ApiOperation("注册")
    @PostMapping("register")
    public ReturnDto<MbUser> register(@Validated(AuthGroup.class) @RequestBody MbUser mbUser) throws JsonProcessingException {
        boolean result = mbUserService.authRegCode(mbUser.getUsername(), mbUser.getCode());
        if (result) {
            boolean register = mbUserService.register(mbUser);
            if (register) return gr.success();
        }
        if (!result) return gr.custom(ResultCode.BAD_REQUEST.getCode(), "验证码无效");
        return gr.fail();
    }

    @AOPLog("登陆")
    @ApiOperation("登陆")
    @PostMapping("login")
    public ReturnDto<MbUser> login(@Validated({LoginGroup.class}) @RequestBody MbUser mbUser) throws JsonProcessingException {
        String loginResult = mbUserService.login(mbUser);
        if (loginResult.equals(ResultCode.OK.getMessage())) return gr.success();
        if (loginResult.equals(ResultCode.NOT_FOUND.getMessage()))
            return gr.custom(ResultCode.NOT_FOUND.getCode(), "用户未注册");
        if (loginResult.equals(ResultCode.USER_ILLEGAL.getMessage()))
            return gr.custom(ResultCode.USER_ILLEGAL.getCode(), ResultCode.USER_ILLEGAL.getMessage());
        return gr.fail();
    }

    /**
     * 通过主键查询单条数据
     *
     * @param uid 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{uid}")
    public MbUser selectOne(@PathVariable("uid") Long uid) {
        return this.mbUserService.queryById(uid);
    }

}