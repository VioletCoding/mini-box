package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbUser)表控制层
 *
 * @author Violet
 * @since 2020-11-18 23:34:57
 */
@RestController
@RequestMapping("mbUser")
public class MbUserController {
    /**
     * 服务对象
     */
    @Resource
    private MbUserService mbUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbUser selectOne(@Validated Long id) {
        return this.mbUserService.queryById(id);
    }

}