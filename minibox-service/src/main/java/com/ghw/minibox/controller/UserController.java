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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author Violet
 * @description
 * @date 2021/1/5
 */
@RestController
@RequestMapping("user")
@Api("用户控制层")
public class UserController {
    @Resource
    private GenerateResult<Object> gr;
    @Resource
    private NimbusJoseJwt jwt;
    @Resource
    private UserImpl user;

    /**
     * post请求防止username拼在url上
     * 单个参数没有实体，使用map来接收请求体json内容
     *
     * @param username 邮箱（本系统用邮箱当成username登陆）
     * @return 统一结果Dto
     * @throws Exception -
     */
    @ApiOperation("发送邮箱验证码")
    @PostMapping("before")
    public ReturnDto<Object> service(@RequestBody Map<String, Object> username) throws Exception {
        user.service((String) username.get("username"));
        return gr.success();
    }

    /**
     * 自动判断登陆还是注册，一键登陆功能，通过邮箱验证码就可以一键实现登陆注册
     * 密码是随机生成的字符串，只能通过修改密码功能来改
     * 两个参数没必要用实体，map接收，post请求原因同上
     *
     * @param params authCode:验证码 username:邮箱
     * @return 统一结果Dto -> 用户部分信息
     * @throws JsonProcessingException -
     * @throws JOSEException           -
     */
    @ApiOperation("自动判断登陆还是注册")
    @PostMapping("after")
    public ReturnDto<Object> doService(@RequestBody Map<String, Object> params) throws JsonProcessingException, JOSEException {

        String authCode = (String) params.get("authCode");
        String username = (String) params.get("username");

        if (authCode.length() < 6) return gr.fail(ResultCode.AUTH_CODE_ERROR);

        return gr.success(user.doService(username, authCode));
    }

    /**
     * 展示用户个人信息
     *
     * @param id 用户id
     * @return 个人非敏感信息
     */
    @ApiOperation("展示用户个人信息")
    @GetMapping("show")
    public ReturnDto<Object> showUserInfo(@RequestParam Long id) throws JsonProcessingException {
        return gr.success(user.showUserInfo(id));
    }

    /**
     * 更新用户个人信息
     *
     * @param mbUser 实例，id必传，使用Hibernate-Validator校验实例里的参数
     * @return 更新后的个人信息
     */
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

    /**
     * 修改密码校验，通过请求头得到token，解密获取token载荷，从而获取username
     *
     * @param request 请求
     * @return 是否通过校验
     * @throws Exception -
     */
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

    /**
     * 校验验证码，配合 beforeModifyPassword 使用
     *
     * @param params  authCode 验证码
     * @param request 请求
     * @return 是否校验通过
     * @throws Exception -
     */
    @ApiOperation("校验验证码")
    @PostMapping("check")
    public Object checkAuthCode(@RequestBody Map<String, Object> params, ServletWebRequest request) throws Exception {

        String token = request.getHeader("accessToken");

        String authCode = (String) params.get("authCode");

        boolean c = StringUtils.isNullOrEmpty(authCode);

        if (!c) {

            PayloadDto payloadDto = jwt.verifyTokenByHMAC(token);
            boolean b = user.authRegCode(payloadDto.getUsername(), authCode);

            if (b) return gr.success();

        }
        return gr.fail();
    }

    /**
     * 校验通过后 修改密码，并从Redis移除当前用户的token，需要重新登陆
     *
     * @param mbUser 实例 id、password必传，使用Hibernate-Validator校验实例里的参数
     * @return 是否成功
     */
    @ApiOperation("修改密码")
    @PostMapping("modify")
    public Object doModifyPassword(@RequestBody @Validated(UpdatePassword.class) MbUser mbUser) {

        boolean b = user.updatePassword(mbUser);

        if (b) return gr.success();

        return gr.fail(ResultCode.AUTH_CODE_ERROR);
    }

    /**
     * 修改用户头像
     *
     * @param userImg 用户头像
     * @param uid     用户id
     * @return 修改过后的信息
     * @throws IOException -
     */
    @ApiOperation("修改用户头像")
    @PostMapping("updateImg")
    public ReturnDto<Object> updateUserImg(@RequestParam MultipartFile userImg, @RequestParam Long uid) throws IOException {

        if (userImg.isEmpty()) return gr.fail();

        return gr.success(user.updateUserImg(userImg, uid));
    }

    /**
     * 登出，删除token
     *
     * @param request 请求
     * @return 是否成功
     * @throws Exception -
     */
    @ApiOperation("登出")
    @GetMapping("logout")
    public Object logout(ServletWebRequest request) throws Exception {
        String accessToken = request.getHeader("accessToken");
        PayloadDto payloadDto = jwt.verifyTokenByHMAC(accessToken);
        user.logout(payloadDto.getUsername());
        return gr.success();
    }


}
