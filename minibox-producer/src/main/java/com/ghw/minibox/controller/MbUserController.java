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
import com.ghw.minibox.validatedgroup.RegisterGroup;
import com.ghw.minibox.validatedgroup.SingleGroup;
import com.nimbusds.jose.JOSEException;
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
    private GenerateResult<String> gr;


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
    public ReturnDto<String> beforeRegister(@Validated(SingleGroup.class) @RequestBody MbUser mbUser) throws EmailException {
        log.info("mbUser==>{}", mbUser);
        boolean queryByUsername = mbUserService.queryByUsername(mbUser.getUsername());
        if (queryByUsername) return gr.success();
        return gr.custom(ResultCode.USER_EXIST.getCode(), ResultCode.USER_EXIST.getMessage());
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
    public ReturnDto<String> register(@Validated({AuthGroup.class, RegisterGroup.class}) @RequestBody MbUser mbUser) {
        boolean result = mbUserService.authRegCode(mbUser.getUsername(), mbUser.getCode());
        if (result) {
            boolean register = mbUserService.register(mbUser);
            if (register) return gr.success();
        }
        if (!result) return gr.custom(ResultCode.BAD_REQUEST.getCode(), "验证码无效");
        return gr.fail();
    }

    /**
     * 登陆API
     *
     * @param mbUser 用户实体
     * @return ReturnDto
     * @throws JsonProcessingException Json解析失败
     * @throws JOSEException           JWT签发失败
     */
    @AOPLog("登陆")
    @ApiOperation("登陆")
    @PostMapping("login")
    public ReturnDto<String> login(@Validated({LoginGroup.class}) @RequestBody MbUser mbUser) throws JsonProcessingException, JOSEException {
        log.info("mbUser==>{}", mbUser);
        String login = mbUserService.login(mbUser);
        if (login != null) return gr.success(login);
        return gr.fail();
    }

    @AOPLog("登出")
    @GetMapping("logout/{token}")
    public ReturnDto<String> logout(@PathVariable String token) throws Exception {
        boolean login = mbUserService.logout(token);
        if (login) return gr.success();
        return gr.fail();
    }

    @AOPLog("忘记密码校验")
    @PostMapping("forget")
    public ReturnDto<String> forgetPassword(@Validated(SingleGroup.class) @RequestBody MbUser mbUser) throws EmailException {
        boolean result = mbUserService.forgetPassword(mbUser);
        if (result) return gr.success().setData(null);
        return gr.fail();
    }

    @AOPLog("重置密码")
    @PostMapping("doReset")
    public ReturnDto<String> doResetPassword(@Validated({RegisterGroup.class}) @RequestBody MbUser mbUser) {
        boolean authRegCode = mbUserService.authRegCode(mbUser.getUsername(), mbUser.getCode());
        if (authRegCode) {
            boolean result = mbUserService.doResetPassword(mbUser);
            if (result) return gr.success();
            return gr.fail();
        }
        return gr.fail();
    }

}