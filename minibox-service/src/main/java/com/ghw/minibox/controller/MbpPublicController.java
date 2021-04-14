package com.ghw.minibox.controller;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.component.QiNiuUtil;
import com.ghw.minibox.mapper.MbpBlockMapper;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.mapper.MbpPhotoMapper;
import com.ghw.minibox.mapper.MbpPostMapper;
import com.ghw.minibox.model.BlockModel;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.PhotoModel;
import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.service.MbpEchartsService;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 公用控制器，一些可以公用的Api
 * @date 2021/1/28
 */
@RestController
@RequestMapping("publicApi")
public class MbpPublicController {
    @Resource
    private QiNiuUtil qiNiuUtil;
    @Resource
    private MbpEchartsService echartsService;
    @Resource
    private MbpPhotoMapper mbpPhotoMapper;
    @Resource
    private MbpBlockMapper mbpBlockMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;
    @Resource
    private MbpPostMapper mbpPostMapper;

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("update")
    public void updateQiNiuLink() {

        String origin = "qqb6nk4ol";
        String target = "qrjrtokf9";

        List<PhotoModel> photoModels = this.mbpPhotoMapper.selectList(null);
        photoModels.forEach(p -> {
            String s = p.getPhotoLink();
            String newLink = s.replace(origin, target);
            p.setPhotoLink(newLink);
            this.mbpPhotoMapper.updateById(p);
        });

        List<BlockModel> blockModels = this.mbpBlockMapper.selectList(null);
        blockModels.forEach(b -> {
            String s = b.getPhotoLink();
            String newLink = s.replace(origin, target);
            b.setPhotoLink(newLink);
            this.mbpBlockMapper.updateById(b);
        });


        List<GameModel> gameModels = mbpGameMapper.selectList(null);
        gameModels.forEach(p -> {
            String s = p.getPhotoLink();
            String newPhotoLink = s.replace(origin, target);
            p.setPhotoLink(newPhotoLink);
            mbpGameMapper.updateById(p);
        });

        List<PostModel> postModels = mbpPostMapper.selectList(null);
        postModels.forEach(p -> {
            String s = p.getPhotoLink();
            String newPhotoLink = s.replace(origin, target);
            p.setPhotoLink(newPhotoLink);
            String content = p.getContent();
            String s1 = content.replace(origin, target);
            p.setContent(s1);
            mbpPostMapper.updateById(p);
        });
    }

    @ApiOperation("上传文件公用接口,可多文件上传,单次单个文件最大3MB,单次全部文件加起来不能超过30MB")
    @PostMapping("upload")
    public ResultVo upload(@RequestParam("multipartFile") MultipartFile[] multipartFile) throws IOException {
        if (multipartFile.length < 1) {
            return Result.fail("文件列表为空");
        }
        Map<String, Object> map = new HashMap<>();
        List<String> linkList = new ArrayList<>();
        for (MultipartFile m : multipartFile) {
            String originalFilename = m.getOriginalFilename();
            String simpleUUID = IdUtil.fastSimpleUUID();
            String fileLink = qiNiuUtil.syncUpload(simpleUUID + originalFilename, m.getBytes());
            linkList.add(fileLink);
        }
        map.put("images", linkList);
        return Result.success(map);
    }

    @ApiOperation("echarts数据接口")
    @GetMapping("echarts")
    public ResultVo echartsData() {
        Map<String, Object> build = echartsService.build();
        return Result.success(build);
    }
}
