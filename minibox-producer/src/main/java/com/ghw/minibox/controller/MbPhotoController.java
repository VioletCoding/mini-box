package com.ghw.minibox.controller;

import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.service.MbPhotoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbPhoto)表控制层
 *
 * @author makejava
 * @since 2020-11-19 00:57:17
 */
@RestController
@RequestMapping("mbPhoto")
public class MbPhotoController {
    /**
     * 服务对象
     */
    @Resource
    private MbPhotoService mbPhotoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public MbPhoto selectOne(Long id) {
        return this.mbPhotoService.queryById(id);
    }

}