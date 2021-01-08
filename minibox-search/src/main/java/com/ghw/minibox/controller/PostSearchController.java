package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.PostSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@RestController
@RequestMapping("search")
public class PostSearchController {
    @Resource
    private PostSearchService searchService;
    @Resource
    private GenerateResult<Object> gr;

    @GetMapping("saveAll")
    public ReturnDto<Object> importAll() throws JsonProcessingException {
        searchService.getData();
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
        Page<MbPost> postPage = searchService.search(title, title, pageNum, pageSize);
        return gr.success(postPage);

    }
}
