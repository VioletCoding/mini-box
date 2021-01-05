package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.impl.UserImpl;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.utils.SendEmail;
import com.ghw.minibox.validatedgroup.UpdatePassword;
import com.nimbusds.jose.JOSEException;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author Violet
 * @description
 * @date 2021/1/5
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private GenerateResult<Object> gr;
    @Resource
    private NimbusJoseJwt jwt;
    @Resource
    private UserImpl user;

    @ApiOperation("发送邮箱验证码")
    @PostMapping("before")
    public ReturnDto<Object> service(@RequestBody Map<String, Object> username) throws Exception {
        user.service((String) username.get("username"));
        return gr.success();
    }

    @ApiOperation("自动判断登陆还是注册，一键登陆功能，通过邮箱验证码就可以一键实现登陆注册，密码是随机生成的字符串，只能通过修改密码功能来改")
    @PostMapping("after")
    public ReturnDto<Object> doService(@RequestBody Map<String, Object> params) throws JsonProcessingException, JOSEException {
        String authCode = (String) params.get("authCode");
        String username = (String) params.get("username");
        if (authCode.length() < 6) {
            return gr.fail(ResultCode.AUTH_CODE_ERROR);
        }
        return gr.success(user.doService(username, authCode));
    }

    @ApiOperation("展示用户个人信息")
    @GetMapping("show")
    public ReturnDto<Object> showUserInfo(@RequestParam Long id) {
        return gr.success(user.showUserInfo(id));
    }

    @ApiOperation("更新用户个人信息")
    @PostMapping("update")
    public ReturnDto<Object> updateUserInfo(@RequestBody MbUser mbUser) {
        boolean update = user.update(mbUser);
        if (update) {
            MbUser user = this.user.selectOne(mbUser.getId());
            return gr.success(user);
        }
        return gr.fail();
    }

    @ApiOperation("修改密码前的校验")
    @GetMapping("beforeModify")
    public ReturnDto<Object> beforeModifyPassword(ServletWebRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");

        if (!StringUtils.isNullOrEmpty(accessToken)) {
            PayloadDto payloadDto = jwt.verifyTokenByHMAC(accessToken);
            PayloadDto dto = Objects.requireNonNull(payloadDto);
            String username = dto.getUsername();
            if (!StringUtils.isNullOrEmpty(username)) {
                user.sendEmail(username, SendEmail.SUBJECT, SendEmail.OTHER_MESSAGE);
                return gr.success();
            }
            return gr.fail(ResultCode.UNAUTHORIZED);
        }
        return gr.fail(ResultCode.UNAUTHORIZED);
    }

    @ApiOperation("校验验证码")
    @PostMapping("check")
    public Object checkAuthCode(@RequestBody Map<String, Object> params, ServletWebRequest request) throws Exception {
        String token = request.getHeader("accessToken");
        String authCode = (String) params.get("authCode");
        System.out.println("打印一下authCode" + authCode);
        boolean c = StringUtils.isNullOrEmpty(authCode);
        if (!c) {
            PayloadDto payloadDto = jwt.verifyTokenByHMAC(token);
            boolean b = user.authRegCode(payloadDto.getUsername(), authCode);
            if (b) {
                return gr.success();
            }
        }
        return gr.fail();
    }

    @ApiOperation("修改密码")
    @PostMapping("modify")
    public Object doModifyPassword(@RequestBody @Validated(UpdatePassword.class) MbUser mbUser) {
        boolean b = user.updatePassword(mbUser);
        if (b) {
            return gr.success();
        }
        return gr.fail(ResultCode.AUTH_CODE_ERROR);
    }

    @ApiOperation("登出")
    @GetMapping("logout")
    public Object logout(ServletWebRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");
        PayloadDto payloadDto = jwt.verifyTokenByHMAC(accessToken);
        user.logout(payloadDto.getUsername());
        return gr.success();
    }


}
