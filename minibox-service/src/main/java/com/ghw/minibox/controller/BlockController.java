package com.ghw.minibox.controller;

import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import com.ghw.minibox.service.impl.BlockImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 版块（社区） 控制器
 * @date 2021/1/4
 */
@RestController
@RequestMapping("block")
@Api("版块控制层")
public class BlockController {
    @Resource
    private BlockImpl block;

    @ApiOperation("版块列表")
    @GetMapping("all")
    public ResultVo showAllBlock() {
        return Result.success(block.selectAll(null));
    }
}
