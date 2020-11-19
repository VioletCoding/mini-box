package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.validatedgroup.RegisterGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @PostMapping("register")
    public ReturnDto<MbUser> register(@Validated(RegisterGroup.class) @RequestBody MbUser mbUser) {
        boolean result = mbUserService.register(mbUser);
        log.info("MbUserController.register()==>{}", result);

        if (result)
            return gr.success();
        return gr.fail();
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{uid}")
    public MbUser selectOne(@PathVariable("uid") Long uid) {
        return this.mbUserService.queryById(uid);
    }

}