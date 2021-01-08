package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.impl.GameSearchImpl;
import com.ghw.minibox.service.impl.PostSearchImpl;
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
    private GenerateResult<Object> gr;

    /**
     * 导入帖子表的内容到ES
     */
    @GetMapping("saveAll")
    public ReturnDto<Object> importAll() {
        postSearch.getData();
        gameSearch.getData();
        return gr.success();
    }

    /**
     * 简单搜索
     *
     * @param title    搜索内容
     * @param pageNum  第几页
     * @param pageSize 每页几个
     * @return 搜索结果
     */
    @GetMapping("simple")
    public ReturnDto<Object> search(@RequestParam(required = false) String title,
                                    @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<MbPost> postPage = postSearch.search(title, pageNum, pageSize);
        Page<MbGame> gamePage = gameSearch.search(title, pageNum, pageSize);
        Map<String, Object> allData = new HashMap<>();
        allData.put("post", postPage);
        allData.put("game", gamePage);
        return gr.success(allData);
    }
}
