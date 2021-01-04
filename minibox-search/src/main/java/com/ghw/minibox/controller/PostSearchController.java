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

    @GetMapping("simple")
    public ReturnDto<Object> search(@RequestParam(required = false) String title,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize) {
        Page<MbPost> postPage = searchService.search(title, pageNum, pageSize);
        return gr.success(postPage);

    }
}
