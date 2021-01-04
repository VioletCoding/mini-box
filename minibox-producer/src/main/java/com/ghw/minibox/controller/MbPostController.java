package com.ghw.minibox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.MbPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * (MbPost)表控制层
 *
 * @author Violet
 * @since 2020-11-19 12:20:17
 */
@RestController
@RequestMapping("post")
@Api("帖子控制层")
public class MbPostController {

    @Resource
    private MbPostService mbPostService;
    @Resource
    private GenerateResult<Object> gr;

    @ApiOperation("帖子列表")
    @GetMapping("showAll")
    public ReturnDto<Object> postList() throws JsonProcessingException {
        return gr.success(mbPostService.showAll(null));
    }


    @ApiOperation("发布帖子")
    @PostMapping("publish")
    public ReturnDto<Object> publishPost(@RequestBody @Validated MbPost mbPost) throws JsonProcessingException {
        boolean b = mbPostService.newPost(mbPost);
        if (b) {
            return gr.success();
        }
        return gr.fail();
    }

    @ApiOperation("帖子图片上传")
    @PostMapping("upload")
    public ReturnDto<Object> addPictureInPost(@RequestParam(value = "multipartFiles") MultipartFile[] multipartFiles) throws IOException {
        Object o = mbPostService.addPictureInPost(multipartFiles);
        return gr.success(o);
    }



    @ApiOperation("用户-在哪个帖子的评论")
    @GetMapping("userCommentShow")
    public ReturnDto<Object> userComment(@RequestParam("uid") Long uid) {
        return gr.success(mbPostService.showPostInUser(uid));
    }

    @ApiOperation("帖子详情展示")
    @GetMapping("detail")
    public ReturnDto<Object> showPostDetail(@RequestParam(value = "tid") Long tid) {
        List<MbPost> postDetail = mbPostService.showPostDetail(tid);
        return gr.success(postDetail);
    }

}