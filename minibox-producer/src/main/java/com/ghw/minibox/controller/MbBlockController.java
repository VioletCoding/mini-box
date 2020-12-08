package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.service.MbBlockService;
import com.ghw.minibox.utils.AOPLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbBlock)表控制层
 *
 * @author makejava
 * @since 2020-11-19 12:20:09
 */
@RestController
@RequestMapping("block")
public class MbBlockController {

    @Resource
    private MbBlockService mbBlockService;


    @AOPLog("获取所有版块的ID和名字")
    @GetMapping("get")
    @ApiOperation("获取所有版块的ID和名字")
    public ReturnDto<List<MbBlock>> showAllBlock() {
        List<MbBlock> blockList = mbBlockService.showAllBlock();
        return new GenerateResult<List<MbBlock>>().success(blockList);
    }

}