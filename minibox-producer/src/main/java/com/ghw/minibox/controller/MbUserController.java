package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.ResultCode;
import com.nimbusds.jose.JOSEException;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


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
    private GenerateResult<String> gr;

    /**
     * 登陆前的校验
     *
     * @param param 用户名
     * @return ReturnDto
     */
    @PostMapping("before")
    public ReturnDto<String> beforeRegister(@RequestBody Map<String, Object> param) throws Exception {
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
            return new GenerateResult<>().fail(ResultCode.AUTH_CODE_ERROR);
        }
        return mbUserService.doService(username, authCode);
    }

    /**
     * 展示用户个人信息
     *
     * @param uid 用户id
     * @return 用户信息
     */
    @GetMapping("show")
    public ReturnDto<MbUser> showUserInfo(@RequestParam("uid") Long uid) {
        MbUser mbUser = mbUserService.queryById(uid);
        return new GenerateResult<MbUser>().success(mbUser);
    }

}