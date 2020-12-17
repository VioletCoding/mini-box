package com.ghw.minibox.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Violet
 * @description 图片上传相关的返回类型
 * @date 2020/12/17
 */
@Data
@Accessors(chain = true)
public class ReturnImgDto {
    private Long photoId;
    private List<Long> photoIdList;
    private String photoImg;
    private List<String> photoImgList;
}
