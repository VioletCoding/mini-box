package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.MbPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@RequestMapping("test")
@RestController
public class TestController {
    @Resource
    private MbPostService mbPostService;
    @Resource
    private GenerateResult<Object> gr;

    @GetMapping("test1")
    public ReturnDto<Object> test(@RequestBody(required = false) MbPost mbPost) {
        return gr.success(mbPostService.showAll(mbPost));
    }
}
