package com.ghw.minibox.controller;

import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.ReplyModel;
import com.ghw.minibox.service.impl.MbpCommentServiceImpl;
import com.ghw.minibox.service.impl.MbpReplyServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/2/2
 */
@RestController
@RequestMapping("commentApi")
public class MbpCommentController {
    @Resource
    private MbpCommentServiceImpl commentService;
    @Resource
    private MbpReplyServiceImpl replyService;

    @PostMapping("add")
    public ResultVo publishComment(@RequestBody CommentModel commentModel) {
        boolean save = commentService.save(commentModel);
        return Result.successFlag(save);
    }

    @PostMapping("reply")
    public ResultVo publishReply(@RequestBody @Validated ReplyModel replyModel) {
        boolean save = replyService.save(replyModel);
        return Result.successFlag(save);
    }
}
