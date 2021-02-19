package com.ghw.minibox.controller;

import com.ghw.minibox.model.TagModel;
import com.ghw.minibox.service.MbpTagService;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 标签控制器
 * @date 2021/2/19
 */
@RestController
@RequestMapping("tagApi")
public class MbpTagController {
    @Resource
    private MbpTagService mbpTagService;

    @PostMapping("save")
    public ResultVo save(@RequestBody @Validated TagModel tagModel) {
        List<TagModel> tagModels = mbpTagService.save(tagModel);
        return Result.success(tagModels);
    }

    @GetMapping("remove")
    public ResultVo remove(@RequestParam Long gameId, @RequestParam Long id) {
        List<TagModel> remove = mbpTagService.remove(id, gameId);
        return Result.success(remove);
    }

    @PostMapping("list")
    public ResultVo list(@RequestBody(required = false) TagModel tagModel) {
        List<TagModel> list = mbpTagService.list(tagModel);
        return Result.success(list);
    }

}
