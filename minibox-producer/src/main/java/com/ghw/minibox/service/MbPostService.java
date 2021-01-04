package com.ghw.minibox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.entity.MbPost;
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
     * @param mbPost 实例
     * @return 列表
     */
    List<MbPost> showAll(MbPost mbPost) throws JsonProcessingException;

    /**
     * 发表新帖子
     *
     * @param mbPost 实例
     * @return 对象
     */
    boolean newPost(MbPost mbPost) throws JsonProcessingException;

    //*********************************重构分割线**********************************
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
     * 上传文件，可以批量上传，文件上传异步
     *
     * @param multipartFiles 文件
     */
    Object addPictureInPost(MultipartFile[] multipartFiles) throws IOException;


}