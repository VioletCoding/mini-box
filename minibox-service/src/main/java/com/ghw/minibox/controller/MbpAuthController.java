package com.ghw.minibox.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.model.UserModel;
import com.ghw.minibox.service.MbpUserService;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.mail.EmailException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Violet
 * @description 认证 控制器
 * @date 2021/2/1
 */
@RestController
@RequestMapping("authApi")
public class MbpAuthController {
    @Resource
    private MbpUserService userService;
    @Resource
    private NimbusJoseJwt nimbusJoseJwt;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 校验用户是否存在，根据存在与否发送不同邮件，邮件内容为验证码，抛出异常则发送失败
     *
     * @param username 邮箱
     */
    @PostMapping("authCode")
    public ResultVo sendAuthCode(@RequestBody Map<String, String> username) throws EmailException {
        userService.sendAuthCode(username.get("username"));
        return Result.success();
    }

    /**
     * 自动登录或注册，登陆或注册成功会返回当前用户的部分信息
     *
     * @param params 邮箱username 验证码authCode
     */
    @PostMapping("auth")
    public ResultVo signInOrSignUp(@RequestBody Map<String,String> params) throws JsonProcessingException, JOSEException {
        String authCode = params.get("authCode");
        String username = params.get("username");
        if (StrUtil.isBlank(username)) {
            throw new MiniBoxException("用户名为空");
        }
        if (StrUtil.isBlank(authCode) || authCode.length() < 6) {
            throw new MiniBoxException("验证码错误");
        }

        Map<String, Object> service = userService.service(username, authCode);
        return Result.success(service);
    }

    /**
     * 密码登陆
     *
     * @param userModel username | password
     * @return 登陆成功后，返回用户信息
     */
    @PostMapping("passwordLogin")
    public ResultVo usernameAndPasswordLogin(@RequestBody UserModel userModel) throws JsonProcessingException, JOSEException {
        if (StrUtil.isBlank(userModel.getUsername())) {
            throw new MiniBoxException("用户名为空");
        }
        if (StrUtil.isBlank(userModel.getPassword())) {
            throw new MiniBoxException("密码为空");
        }

        Map<String, Object> map = userService.usingPasswordLogin(userModel.getUsername(), userModel.getPassword());
        return Result.success(map);
    }

    /**
     * 登出
     */
    @GetMapping("logout")
    public ResultVo logout(HttpServletRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");
        PayloadDto payloadDto = nimbusJoseJwt.verifyTokenByHMAC(accessToken);
        String username = payloadDto.getUsername();
        boolean remove = redisUtil.remove(RedisUtil.TOKEN_PREFIX + username);
        return Result.successFlag(remove);
    }
}
