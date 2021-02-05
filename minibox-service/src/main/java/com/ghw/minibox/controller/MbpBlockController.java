package com.ghw.minibox.controller;

import com.ghw.minibox.model.BlockModel;
import com.ghw.minibox.service.impl.MbpBlockServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增版块
     *
     * @param blockModel 实体
     * @return 新的版块列表
     */
    @PostMapping("add")
    public ResultVo add(@RequestBody @Validated BlockModel blockModel) {
        List<BlockModel> models = blockService.addBlock(blockModel);
        return Result.success(models);
    }

    /**
     * 修改版块
     *
     * @param blockModel 实体
     * @return 如果成功 返回新的版块列表
     */
    @PostMapping("modify")
    public ResultVo modify(@RequestBody @Validated BlockModel blockModel) {
        List<BlockModel> modify = blockService.modify(blockModel);
        return Result.success(modify);
    }

    /**
     * 删除版块
     *
     * @param id 版块id
     * @return 新的版块列表
     */
    @GetMapping("delete")
    public ResultVo remove(@RequestParam Long id) {
        List<BlockModel> delete = blockService.delete(id);
        return Result.success(delete);
    }
}
