package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.service.MbCommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbComment)表控制层
 *
 * @author makejava
 * @since 2020-11-19 00:56:51
 */
@RestController
@RequestMapping("mbComment")
public class MbCommentController {
    /**
     * 服务对象
     */
    @Resource
    private MbCommentService mbCommentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbComment selectOne(Long id) {
        return this.mbCommentService.queryById(id);
    }

}