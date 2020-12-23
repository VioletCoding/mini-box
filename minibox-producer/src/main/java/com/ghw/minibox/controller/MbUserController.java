package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.ResultCode;
import io.swagger.annotations.Api;
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
@Api("用户控制层")
public class MbUserController {
    @Resource
    private MbUserService mbUserService;
    @Resource
    private GenerateResult<String> gr;


    /**
     * 登陆前的校验
     *
     * @param username 用户名
     * @return ReturnDto
     */
    @PostMapping("beforeLogin")
    public ReturnDto<String> beforeRegister(@RequestParam("username") String username) throws Exception {
        boolean exist = mbUserService.exist(username);
        if (exist) {
            return gr.success();
        }
        return gr.custom(ResultCode.USER_EXIST.getCode(), ResultCode.USER_EXIST.getMessage());
    }

    /**
     * 展示用户个人信息，暂时这么写，应该从token获取uid
     *
     * @param uid 用户id
     * @return 用户信息
     */
    //TODO
    @GetMapping("show")
    public ReturnDto<MbUser> showUserInfo(@RequestParam("uid") Long uid) {
        MbUser mbUser = mbUserService.queryById(uid);
        return new GenerateResult<MbUser>().success(mbUser);
    }

}