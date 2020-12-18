package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.service.MbGameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbGame)表控制层
 *
 * @author Violet
 * @since 2020-11-19 12:20:13
 */
@RestController
@RequestMapping("game")
public class MbGameController {
    /**
     * 服务对象
     */
    @Resource
    private MbGameService mbGameService;


    @GetMapping("all")
    public ReturnDto<List<MbGame>> showGameList() {
        return new GenerateResult<List<MbGame>>().success(mbGameService.showGameList());
    }


    @GetMapping("detail")
    public ReturnDto<MbGame> showGameDetail(@RequestParam("gid") Long gid) {
        return new GenerateResult<MbGame>().success(mbGameService.showGameDetail(gid));
    }

}