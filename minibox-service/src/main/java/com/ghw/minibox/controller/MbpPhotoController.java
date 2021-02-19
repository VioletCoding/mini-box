package com.ghw.minibox.controller;

import com.ghw.minibox.model.PhotoModel;
import com.ghw.minibox.service.MbpPhotoService;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 游戏图片 控制层
 * @date 2021/2/19
 */
@RestController
@RequestMapping("photoApi")
public class MbpPhotoController {
    @Resource
    private MbpPhotoService photoService;

    @PostMapping("save")
    public ResultVo save(@RequestBody @Validated PhotoModel photoModel) {
        List<PhotoModel> photoModels = photoService.save(photoModel);
        return Result.success(photoModels);
    }

    @GetMapping("remove")
    public ResultVo remove(@RequestParam Long id, @RequestParam Long gameId) {
        List<PhotoModel> photoModels = photoService.remove(id, gameId);
        return Result.success(photoModels);
    }

    @PostMapping("list")
    public ResultVo remove(@RequestBody(required = false) PhotoModel photoModel) {
        List<PhotoModel> photoModels = photoService.list(photoModel);
        return Result.success(photoModels);
    }

}
