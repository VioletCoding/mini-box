package com.ghw.minibox.controller;

import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.ReplyModel;
import com.ghw.minibox.service.impl.MbpCommentService;
import com.ghw.minibox.service.impl.MbpReplyService;
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
 * @description 评论 控制层
 * @date 2021/2/2
 */
@RestController
@RequestMapping("commentApi")
public class MbpCommentController {
    @Resource
    private MbpCommentService commentService;
    @Resource
    private MbpReplyService replyService;

    /**
     * 发表评论
     *
     * @param commentModel 实体
     * @return 是否成功
     */
    @PostMapping("add")
    public ResultVo publishComment(@RequestBody @Validated CommentModel commentModel) {
        boolean save = commentService.save(commentModel);
        return Result.successFlag(save);
    }

    /**
     * 回复评论
     *
     * @param replyModel 实体
     * @return 是否成功
     */
    @PostMapping("reply")
    public ResultVo publishReply(@RequestBody @Validated ReplyModel replyModel) {
        boolean save = replyService.save(replyModel);
        return Result.successFlag(save);
    }
}
