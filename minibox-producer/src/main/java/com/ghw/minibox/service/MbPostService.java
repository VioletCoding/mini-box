package com.ghw.minibox.service;

import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.transaction.annotation.Transactional;
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
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    MbPost queryById(Long tid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbPost> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
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
    boolean addPictureInPost(MultipartFile[] multipartFiles, Long tid) throws IOException;

    /**
     * 修改数据
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    MbPost update(MbPost mbPost);

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    boolean deleteById(Long tid);

}