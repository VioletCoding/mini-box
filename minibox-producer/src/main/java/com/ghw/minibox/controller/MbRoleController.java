package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbRole;
import com.ghw.minibox.service.MbRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbRole)表控制层
 *
 * @author makejava
 * @since 2020-11-19 00:57:49
 */
@RestController
@RequestMapping("mbRole")
public class MbRoleController {
    /**
     * 服务对象
     */
    @Resource
    private MbRoleService mbRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbRole selectOne(Long id) {
        return this.mbRoleService.queryById(id);
    }

}