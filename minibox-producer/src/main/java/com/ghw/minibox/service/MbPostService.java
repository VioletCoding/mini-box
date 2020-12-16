package com.ghw.minibox.service;

import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    List<MbPost> showPostList();

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    MbPost showPostDetail(Long tid);


    MbPost queryById(Long tid);


    /**
     * 发表新帖子
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    @Transactional
    ReturnDto<ResultCode> publish(MbPost mbPost);

    /**
     * 上传文件，可以批量上传，异步接口
     *
     * @param multipartFiles 文件
     * @param tid            帖子ID
     */
    Map<String,String> addPictureInPost(MultipartFile[] multipartFiles, Long tid) throws IOException;

}