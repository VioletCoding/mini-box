package com.ghw.minibox.controller;

import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.service.MbpGameService;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 游戏 控制层
 * @date 2021/2/2
 */
@RestController
@RequestMapping("gameApi")
public class MbpGameController {
    @Resource
    private MbpGameService gameService;

    /**
     * 游戏列表，可以条件查询
     *
     * @param gameModel 游戏列表
     * @return 游戏列表
     */
    @PostMapping("list")
    public ResultVo gameList(@RequestBody(required = false) GameModel gameModel) {
        List<GameModel> gameModels = gameService.findByModel(gameModel);
        return Result.success(gameModels);
    }

    /**
     * 游戏详情
     *
     * @param id 游戏id
     * @return 游戏详情
     */
    @GetMapping("detail")
    public ResultVo gameDetail(@RequestParam Long id) {
        Map<String, Object> gameDetail = gameService.gameDetail(id);
        return Result.success(gameDetail);
    }

    /**
     * 添加游戏
     *
     * @param gameModel 实体
     * @return 游戏列表
     */
    @PostMapping("add")
    public ResultVo gameAdd(@RequestBody @Validated GameModel gameModel) {
        List<GameModel> gameModels = gameService.addGame(gameModel);
        return Result.success(gameModels);
    }

    /**
     * 修改游戏
     *
     * @param gameModel 实体
     * @return 游戏列表
     */
    @PostMapping("modify")
    public ResultVo gameModify(@RequestBody @Validated GameModel gameModel) {
        List<GameModel> gameModels = gameService.modifyGame(gameModel);
        return Result.success(gameModels);
    }

    /**
     * 删除游戏
     *
     * @param id 游戏id
     * @return 游戏列表
     */
    @GetMapping("delete")
    public ResultVo gameDelete(@RequestParam Long id) {
        List<GameModel> gameModels = gameService.removeGame(id);
        return Result.success(gameModels);
    }
}
