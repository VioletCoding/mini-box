package com.ghw.minibox.controller;

import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import com.ghw.minibox.es.ESGameModel;
import com.ghw.minibox.es.ESPostModel;
import com.ghw.minibox.service.impl.GameSearchServiceImpl;
import com.ghw.minibox.service.impl.PostSearchServiceImpl;
import com.ghw.minibox.util.RefreshDataUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@RestController
@RequestMapping("searchApi")
public class SearchController {
    @Resource
    private PostSearchServiceImpl postSearchService;
    @Resource
    private GameSearchServiceImpl gameSearchService;
    @Resource
    private RefreshDataUtil refreshDataUtil;

    /**
     * 简单搜索
     *
     * @param title    搜索内容
     * @param pageNum  第几页
     * @param pageSize 每页几个
     * @return 搜索结果
     */
    @GetMapping("search")
    public ResultVo search(@RequestParam(required = false) String title,
                           @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<ESPostModel> postPage = postSearchService.search(title, pageNum, pageSize);
        Page<ESGameModel> gamePage = gameSearchService.search(title, pageNum, pageSize);
        Map<String, Object> allData = new HashMap<>();
        allData.put("post", postPage);
        allData.put("game", gamePage);
        return Result.success(allData);
    }

    /**
     * 刷新ElasticSearch数据
     *
     * @return 是否成功
     */
    @GetMapping("refresh")
    public ResultVo refreshData() {
        refreshDataUtil.refresh();
        return Result.success();
    }
}
