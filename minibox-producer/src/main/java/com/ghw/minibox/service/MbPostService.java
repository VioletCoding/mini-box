package com.ghw.minibox.service;

import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.dto.ReturnImgDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * (MbPost)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 */
public interface MbPostService {


    /**
     * 在首页显示帖子列表
     *
     * @return 帖子列表
     */
    List<MbPost> showPostList(MbPost mbPost);

    /**
     * 显示用户的所有评论在哪些帖子
     *
     * @param uid 用户id
     * @return 信息
     */
    List<MbPost> showPostInUser(Long uid);

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    List<MbPost> showPostDetail(Long tid);


    /**
     * 发表新帖子
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    ReturnDto<ResultCode> publish(MbPost mbPost);

    /**
     * 上传文件，可以批量上传，文件上传异步
     *
     * @param multipartFiles 文件
     */
    ReturnImgDto addPictureInPost(MultipartFile[] multipartFiles) throws IOException;


}