package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.service.MbGameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbGame)表控制层
 *
 * @author makejava
 * @since 2020-11-19 12:20:13
 */
@RestController
@RequestMapping("mbGame")
public class MbGameController {
    /**
     * 服务对象
     */
    @Resource
    private MbGameService mbGameService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbGame selectOne(Long id) {
        return this.mbGameService.queryById(id);
    }

}