package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbReply;
import com.ghw.minibox.service.impl.CommentImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 评论 控制器
 * @date 2021/1/4
 */
@RestController
@RequestMapping("comment")
@Api("评论控制层")
public class CommentController {
    @Resource
    private GenerateResult<Object> gr;
    @Resource
    private CommentImpl comment;

    @ApiOperation("发表评论 -> 必传参数看 入参实体 里的Hibernate-Validator注解")
    @PostMapping("post")
    public ReturnDto<Object> publish(@RequestBody @Validated MbComment mbComment) throws JsonProcessingException {
        boolean insert = comment.insert(mbComment);
        if (insert) {
            return gr.success();
        }
        return gr.fail();
    }

    @ApiOperation("发表回复 -> 必传参数看 入参实体 里的Hibernate-Validator注解")
    @PostMapping("reply")
    public ReturnDto<Object> reply(@RequestBody @Validated MbReply mbReply) {
        boolean reply = comment.Reply(mbReply);
        if (reply) {
            return gr.success();
        }
        return gr.fail();
    }

}
