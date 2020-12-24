package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
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

    @Resource
    private RedisUtil r;

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

    @GetMapping("test")
    public void test() throws JsonProcessingException {
        MbUser mbUser = new MbUser().setDescription("这里是中文");
        r.set("1111", new ObjectMapper().writeValueAsString(mbUser), 300L);
    }

    /**
     * 登陆或者注册（自动判断）
     *
     * @param username 邮箱
     * @param authCode 验证码
     * @return 如果验证码正确，自动判断是登陆还是注册，都会返回token和用户信息（非敏感数据）
     */
    @PostMapping("after")
    public Object doService(@RequestParam("username") String username, @RequestParam("authCode") String authCode) throws JsonProcessingException, JOSEException {
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