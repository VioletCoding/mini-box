package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.dto.ReturnImgDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.MbPostService;
import com.ghw.minibox.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Api("帖子控制层")
public class MbPostController {

    @Resource
    private MbPostService mbPostService;
    @Resource
    private GenerateResult<String> gr;

    @ApiOperation("发布帖子")
    @PostMapping("publish")
    public ReturnDto<String> publishPost(@RequestBody @Validated MbPost mbPost) {
        ReturnDto<ResultCode> returnDto = mbPostService.publish(mbPost);
        if (returnDto.getCode() == ResultCode.OK.getCode()) {
            return gr.success();
        }
        if (returnDto.getCode() == ResultCode.NOT_FOUND.getCode()) {
            return gr.custom(ResultCode.NOT_FOUND.getCode(), "该用户不存在");
        }
        return gr.fail();
    }

    @ApiOperation("帖子图片上传")
    @PostMapping("upload")
    public ReturnDto<ReturnImgDto> addPictureInPost(@RequestParam(value = "multipartFiles") MultipartFile[] multipartFiles) throws IOException {
        ReturnImgDto dto = mbPostService.addPictureInPost(multipartFiles);
        return new GenerateResult<ReturnImgDto>().success(dto);
    }

    @ApiOperation("帖子列表显示")
    @GetMapping("showAll")
    public ReturnDto<List<MbPost>> showAllPost() {
        List<MbPost> mbPostList = mbPostService.showPostList();
        return new GenerateResult<List<MbPost>>().success(mbPostList);
    }

    @ApiModelProperty("帖子详情展示")
    @GetMapping("detail")
    public ReturnDto<MbPost> showPostDetail(@RequestParam(value = "tid") Long tid) {
        MbPost postDetail = mbPostService.showPostDetail(tid);
        return new GenerateResult<MbPost>().success(postDetail);
    }

}