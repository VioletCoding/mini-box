package com.ghw.minibox.controller;

import com.ghw.minibox.component.Result;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.es.ESMbGame;
import com.ghw.minibox.es.ESMbPost;
import com.ghw.minibox.service.impl.GameSearchImpl;
import com.ghw.minibox.service.impl.PostSearchImpl;
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
@RequestMapping("search")
public class SearchController {
    @Resource
    private PostSearchImpl postSearch;
    @Resource
    private GameSearchImpl gameSearch;
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
    @GetMapping("simple")
    public ReturnDto search(@RequestParam(required = false) String title,
                                    @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<ESMbPost> postPage = postSearch.search(title, pageNum, pageSize);
        Page<ESMbGame> gamePage = gameSearch.search(title, pageNum, pageSize);
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
    public ReturnDto refreshData() {
        refreshDataUtil.refresh();
        return Result.success();
    }
}
