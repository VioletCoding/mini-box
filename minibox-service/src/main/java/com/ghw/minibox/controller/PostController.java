package com.ghw.minibox.controller;

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

    @ApiOperation("获取帖子列表")
    @GetMapping("all")
    public ReturnDto<Object> showPostList(MbPost mbPost) {
        return gr.success(post.selectAll(mbPost));
    }

    @ApiOperation("发布帖子")
    @PostMapping("publish")
    public ReturnDto<Object> publishPost(@RequestBody @Validated MbPost mbPost) {
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


    @ApiOperation("帖子详情")
    @GetMapping("detail")
    public ReturnDto<Object> showPostDetail(@RequestParam Long id) {
        MbPost mbPost = post.selectOne(id);
        return gr.success(mbPost);
    }


}
