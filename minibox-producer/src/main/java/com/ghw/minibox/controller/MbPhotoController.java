package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.service.MbPhotoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * (MbPhoto)表控制层
 *
 * @author Violet
 * @since 2020-11-19 12:20:15
 */
@RestController
@RequestMapping("photo")
public class MbPhotoController {

    @Resource
    private MbPhotoService mbPhotoService;

    @PostMapping("updateImg")
    public ReturnDto<MbPhoto> updateUserImg(@RequestParam("userImg") MultipartFile userImg, @RequestParam("uid") Long uid) throws IOException {
        MbPhoto mbPhoto = mbPhotoService.update(userImg, uid);
        return new GenerateResult<MbPhoto>().success(mbPhoto);
    }

}