package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.service.MbCommentService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.validatedgroup.CommentInGameGroup;
import com.ghw.minibox.validatedgroup.CommentInPostGroup;
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
 * @author makejava
 * @since 2020-11-19 12:20:11
 */
@RestController
@RequestMapping("comment")
public class MbCommentController {
    /**
     * 服务对象
     */
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

}