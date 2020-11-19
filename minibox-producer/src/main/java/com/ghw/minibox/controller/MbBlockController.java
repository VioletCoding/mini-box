package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.service.MbBlockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbBlock)表控制层
 *
 * @author makejava
 * @since 2020-11-19 12:20:09
 */
@RestController
@RequestMapping("mbBlock")
public class MbBlockController {
    /**
     * 服务对象
     */
    @Resource
    private MbBlockService mbBlockService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbBlock selectOne(Long id) {
        return this.mbBlockService.queryById(id);
    }

}