package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Resource
    private GenerateResult<Object> gr;

}
