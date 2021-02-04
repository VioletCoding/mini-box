package com.ghw.minibox.controller;

import com.ghw.minibox.model.BlockModel;
import com.ghw.minibox.service.impl.MbpBlockServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 版块控制层
 * @date 2021/2/1
 */
@RestController
@RequestMapping("blockApi")
public class MbpBlockController {
    @Resource
    private MbpBlockServiceImpl blockService;

    /**
     * 版块列表，可以条件查询
     *
     * @param blockModel 实体
     * @return 版块列表
     */
    @PostMapping("list")
    public ResultVo list(@RequestBody(required = false) BlockModel blockModel) {
        List<BlockModel> blockModelList = blockService.findByModel(blockModel);
        return Result.success(blockModelList);
    }
}
