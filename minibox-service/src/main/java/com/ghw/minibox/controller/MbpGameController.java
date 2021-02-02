package com.ghw.minibox.controller;

import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.service.impl.MbpGameServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
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
    private MbpGameServiceImpl gameService;

    @PostMapping("list")
    public ResultVo gameList(@RequestBody(required = false) GameModel gameModel) {
        List<GameModel> gameModels = gameService.findByModel(gameModel);
        return Result.success(gameModels);
    }

    @GetMapping("detail")
    public ResultVo gameDetail(@RequestParam Long id) {
        Map<String, Object> gameDetail = gameService.gameDetail(id);
        return Result.success(gameDetail);
    }
}
