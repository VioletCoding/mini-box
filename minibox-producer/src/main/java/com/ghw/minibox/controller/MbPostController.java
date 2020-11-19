package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.MbPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbPost)表控制层
 *
 * @author makejava
 * @since 2020-11-19 12:20:17
 */
@RestController
@RequestMapping("mbPost")
public class MbPostController {
    /**
     * 服务对象
     */
    @Resource
    private MbPostService mbPostService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbPost selectOne(Long id) {
        return this.mbPostService.queryById(id);
    }

}