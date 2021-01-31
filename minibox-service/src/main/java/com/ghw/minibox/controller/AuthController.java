package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.service.impl.MbpUserServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.mail.EmailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Violet
 * @description 认证 控制器
 * @date 2021/2/1
 */
@RestController
@RequestMapping("authApi")
public class AuthController {
    @Resource
    private MbpUserServiceImpl userService;

    /**
     * 校验用户是否存在，根据存在与否发送不同邮件，邮件内容为验证码，抛出异常则发送失败
     *
     * @param username 邮箱
     */
    @PostMapping("authCode")
    public ResultVo sendAuthCode(@RequestParam String username) throws EmailException {
        userService.sendAuthCode(username);
        return Result.success();
    }

    /**
     * 自动登录或注册，登陆或注册成功会返回当前用户的部分信息
     *
     * @param username
     * @param authCode
     * @return
     */
    @PostMapping("auth")
    public ResultVo signInOrSignUp(@RequestParam String username, @RequestParam String authCode)
            throws JsonProcessingException, JOSEException {
        if (authCode.length() < 6) {
            return Result.fail("验证码错误");
        }
        Map<String, Object> service = userService.service(username, authCode);
        return Result.success(service);
    }
}
