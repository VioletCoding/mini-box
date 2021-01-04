package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.service.impl.BlockImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@RestController
@RequestMapping("block")
public class BlockController {
    @Resource
    private GenerateResult<Object> gr;
    @Resource
    private BlockImpl block;

    @ApiOperation("版块列表")
    @GetMapping("all")
    public ReturnDto<Object> showAllBlock() {
        return gr.success(block.selectAll(null));
    }
}
