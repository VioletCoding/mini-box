package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.impl.PostImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@RequestMapping("post")
@RestController
@Api("帖子控制层")
public class PostController {
    @Resource
    private PostImpl post;
    @Resource
    private GenerateResult<Object> gr;

    @ApiOperation("获取帖子列表 -> 可选传入用户id，那么返回就是那一个用户的所有帖子")
    @GetMapping("all")
    public ReturnDto<Object> showPostList(@RequestParam(required = false) Long uid) throws JsonProcessingException {
        return gr.success(post.selectAll(new MbPost().setUid(uid)));
    }

    @ApiOperation("发布帖子 -> 入参要求看实体的注解")
    @PostMapping("publish")
    public ReturnDto<Object> publishPost(@RequestBody @Validated MbPost mbPost) throws JsonProcessingException {
        boolean insert = post.insert(mbPost);
        if (insert) {
            return gr.success();
        }
        return gr.fail();
    }


    @ApiOperation("文件上传，可以批量上传，返回图片的链接，本项目是七牛云链接")
    @PostMapping("upload")
    public ReturnDto<Object> upload(@RequestParam MultipartFile[] multipartFiles) throws IOException {
        Map<String, Object> upload = post.upload(multipartFiles);
        return gr.success(upload);
    }


    @ApiOperation("帖子详情，传入帖子id")
    @GetMapping("detail")
    public ReturnDto<Object> showPostDetail(@RequestParam Long id) throws JsonProcessingException {
        MbPost mbPost = post.selectOne(id);
        return gr.success(mbPost);
    }

    @ApiOperation("用户个人信息显示自己的评论，以及评论在哪个帖子下发布的，传入用户id")
    @GetMapping("userCommentShow")
    public ReturnDto<Object> userCommentByUid(@RequestParam Long uid) {
        return gr.success(post.getCommentAndPostByUid(uid));
    }


}
