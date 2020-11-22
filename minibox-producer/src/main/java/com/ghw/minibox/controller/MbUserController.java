package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.validatedgroup.AuthGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * (MbUser)表控制层
 *
 * @author makejava
 * @since 2020-11-19 12:20:22
 */
@RestController
@RequestMapping("user")
@Slf4j
public class MbUserController {

    @Resource
    private MbUserService mbUserService;

    @Resource
    private GenerateResult<MbUser> gr;

    /**
     * 登陆前的校验
     *
     * @param mbUser 实体
     * @return ReturnDto
     */
    @PostMapping("beforeRegister")
    public ReturnDto<MbUser> beforeRegister(@Validated(AuthGroup.class) @RequestBody MbUser mbUser) throws EmailException, InterruptedException, ExecutionException {
        log.info("进入beforeRegister()方法");
        Boolean result = mbUserService.queryByUsername(mbUser.getUsername());
        if (result) {
            mbUserService.sendEmail(mbUser.getUsername());
            return gr.custom(ResultCode.OK.getCode(), "验证码发送成功");
        }
        return gr.fail();
    }

    /**
     * 校验完后的注册
     *
     * @param mbUser 实体
     * @return ReturnDto
     */
    @PostMapping("register")
    public ReturnDto<MbUser> register(@Validated(AuthGroup.class) @RequestBody MbUser mbUser) {
        log.info("接收到的MbUser==>{}", mbUser);
        boolean result = mbUserService.authRegCode(mbUser.getUsername(), mbUser.getCode());

        if (result) {
            boolean register = mbUserService.register(mbUser);
            if (register) return gr.success();
        }
        if (!result) {
            return gr.custom(ResultCode.BAD_REQUEST.getCode(), "验证码无效");
        }
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