package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.utils.SendEmail;
import com.ghw.minibox.validatedgroup.UpdatePassword;
import com.nimbusds.jose.JOSEException;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;


/**
 * (MbUser)表控制层
 *
 * @author Violet
 * @since 2020-11-19 12:20:22
 */
@RestController
@RequestMapping("user")
@Api("用户控制层")
public class MbUserController {
    @Resource
    private MbUserService mbUserService;
    @Resource
    private GenerateResult<Object> gr;
    @Resource
    private NimbusJoseJwt jwt;

    /**
     * 登陆前的校验
     *
     * @param param 用户名
     * @return ReturnDto
     */
    @PostMapping("before")
    public Object beforeRegister(@RequestBody Map<String, Object> param) throws Exception {
        mbUserService.service((String) param.get("username"));
        return gr.success();
    }

    /**
     * 登陆或者注册（自动判断）
     *
     * @param params map集合，用来接收单个参数，或多个不为同一实体的参数，不用封装成对象即可接收json，而不是接收form-data
     *               为了安全使用POST请求，由于authCode并不存在于实体中，所以这么做
     * @return 如果验证码正确，自动判断是登陆还是注册，都会返回token和用户信息（非敏感数据）
     */
    @PostMapping("after")
    public Object doService(@RequestBody Map<String, Object> params) throws JsonProcessingException, JOSEException {
        String authCode = (String) params.get("authCode");
        String username = (String) params.get("username");
        if (authCode.length() < 6) {
            return gr.fail(ResultCode.AUTH_CODE_ERROR);
        }
        return gr.success(mbUserService.doService(username, authCode));
    }

    /**
     * 展示用户个人信息
     *
     * @param uid 用户id
     * @return 用户信息
     */
    @GetMapping("show")
    public Object showUserInfo(@RequestParam("uid") Long uid) {
        MbUser mbUser = mbUserService.queryById(uid);
        return gr.success(mbUser);
    }

    /**
     * 更新用户个人信息
     *
     * @param mbUser 实例对象
     * @return 更新后的个人信息
     */
    @PostMapping("update")
    public Object updateUserInfo(@RequestBody MbUser mbUser) {
        return gr.success(mbUserService.updateUserInfo(mbUser));
    }

    @ApiOperation("修改密码前的校验")
    @GetMapping("beforeModify")
    public Object beforeModifyPassword(ServletWebRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");

        if (!StringUtils.isNullOrEmpty(accessToken)) {
            PayloadDto payloadDto = jwt.verifyTokenByHMAC(accessToken);
            PayloadDto dto = Objects.requireNonNull(payloadDto);
            String username = dto.getUsername();
            if (!StringUtils.isNullOrEmpty(username)) {
                mbUserService.sendEmail(username, SendEmail.SUBJECT, SendEmail.OTHER_MESSAGE);
                return gr.success();
            }
            return gr.fail(ResultCode.UNAUTHORIZED);
        }
        return gr.fail(ResultCode.UNAUTHORIZED);
    }

    /**
     * 校验验证码
     *
     * @param params  authCode必传
     * @param request 从请求头拿token解密
     */
    @PostMapping("check")
    public Object checkAuthCode(@RequestBody Map<String, Object> params, ServletWebRequest request) throws Exception {
        String token = request.getHeader("accessToken");
        String authCode = (String) params.get("authCode");
        System.out.println("打印一下authCode" + authCode);
        boolean c = StringUtils.isNullOrEmpty(authCode);
        if (!c) {
            PayloadDto payloadDto = jwt.verifyTokenByHMAC(token);
            boolean b = mbUserService.authRegCode(payloadDto.getUsername(), authCode);
            if (b) {
                return gr.success();
            }
        }
        return gr.fail();
    }

    /**
     * 修改密码
     *
     * @param mbUser uid、password必传
     */
    @PostMapping("modify")
    public Object doModifyPassword(@RequestBody @Validated(UpdatePassword.class) MbUser mbUser) {
        boolean b = mbUserService.updatePassword(mbUser);
        if (b) {
            return gr.success();
        }
        return gr.fail(ResultCode.AUTH_CODE_ERROR);
    }

    @GetMapping("logout")
    public Object logout(ServletWebRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");
        PayloadDto payloadDto = jwt.verifyTokenByHMAC(accessToken);
        mbUserService.logout(payloadDto.getUsername());
        return gr.success();
    }
}