package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.service.impl.GameImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/1/5
 */
@Api("游戏控制层")
@RestController
@RequestMapping("game")
public class GameController {
    @Resource
    private GenerateResult<Object> gr;
    @Resource
    private GameImpl game;

    @ApiOperation("游戏列表")
    @GetMapping("all")
    public ReturnDto<Object> showAllGame() {
        return gr.success(game.selectAll(null));
    }

    @ApiOperation("游戏详情")
    @GetMapping("detail")
    public ReturnDto<Object> showGameDetail(@RequestParam Long id) {
        return gr.success(game.selectOne(id));
    }

}
