package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbTag;
import com.ghw.minibox.service.MbTagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbTag)表控制层
 *
 * @author makejava
 * @since 2020-11-19 00:57:59
 */
@RestController
@RequestMapping("mbTag")
public class MbTagController {
    /**
     * 服务对象
     */
    @Resource
    private MbTagService mbTagService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbTag selectOne(Long id) {
        return this.mbTagService.queryById(id);
    }

}