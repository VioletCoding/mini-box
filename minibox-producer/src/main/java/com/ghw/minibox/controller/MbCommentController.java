package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbReply;
import com.ghw.minibox.service.MbCommentService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.validatedgroup.CommentInGameGroup;
import com.ghw.minibox.validatedgroup.CommentInPostGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MbComment)表控制层
 *
 * @author Violet
 * @since 2020-11-19 12:20:11
 */
@RestController
@RequestMapping("comment")
@Api("评论控制层")
public class MbCommentController {
    @Resource
    private MbCommentService mbCommentService;
    @Resource
    private GenerateResult<ResultCode> gr;


    @ApiOperation("发表评论")
    @AOPLog("发表评论")
    @PostMapping("post")
    public ReturnDto<ResultCode> postComment(@RequestBody
                                             @Validated({CommentInPostGroup.class, CommentInGameGroup.class})
                                                     MbComment mbComment) {
        return gr.fromService(mbCommentService.postComment(mbComment));
    }

    @ApiOperation("发表回复")
    @AOPLog("发表回复")
    @PostMapping("reply")
    public ReturnDto<ResultCode> postReply(@RequestBody @Validated MbReply mbReply) {
        return gr.fromService(mbCommentService.postReply(mbReply));
    }

}