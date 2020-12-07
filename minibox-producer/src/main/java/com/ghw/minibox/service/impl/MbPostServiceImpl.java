package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.mapper.MbPostMapper;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.MbPostService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.PostType;
import com.ghw.minibox.utils.QiNiuUtil;
import com.ghw.minibox.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * (MbPost)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 */
@Service
@Slf4j
public class MbPostServiceImpl implements MbPostService {
    @Resource
    private MbPostMapper mbPostMapper;
    @Resource
    private MbUserMapper mbUserMapper;
    @Resource
    private MbPhotoMapper mbPhotoMapper;
    //@Resource
    //private MbCommentMapper mbCommentMapper;
    @Resource
    private GenerateResult<ResultCode> gr;
    @Resource
    private QiNiuUtil qn;

    @Value("${qiNiu.accessKey}")
    private String ak;
    @Value("${qiNiu.secretKey}")
    private String sk;
    @Value("${qiNiu.bucket}")
    private String bucket;
    @Value("${qiNiu.link}")
    private String link;


    /**
     * 在首页显示帖子列表，通过PageHelper分页
     * countComment该帖子内有多少条评论
     * 循环遍历帖子的图片，获取头图的第一张图片的链接作为头图
     *
     * @return 分页过后的帖子列表
     */
    @AOPLog("首页帖子列表")
    @Override
    public List<MbPost> showPostList(int pageNum, int pageSize) {
        //PageHelper.startPage(pageNum, pageSize);
        List<MbPost> mbPostList = mbPostMapper.showPostList();
        for (MbPost m : mbPostList) {
            List<MbPhoto> photoList = m.getPhotoList();
            MbPhoto photo = photoList.get(0);
            m.setHeadPhotoLink(photo.getLink());
        }
        return mbPostList;
    }


    /**
     * 展示帖子详情
     *
     * @param tid 主键
     * @return 帖子详情，帖子作者，评论列表，评论谁发的
     */
    @Override
    public MbPost showPostDetail(Long tid) {
        return mbPostMapper.showPostDetail(tid);
    }

    /**
     * 新增数据
     *
     * @param mbPost 实例对象
     * @return 统一结果
     */
    @AOPLog("发布帖子")
    @Override
    public ReturnDto<ResultCode> publish(MbPost mbPost) {
        MbUser mbUser = mbUserMapper.queryById(mbPost.getUid());
        if (mbUser == null) return gr.custom(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage());
        int result = mbPostMapper.insert(mbPost);
        if (result > 0)
            return gr.success();
        return gr.fail();
    }

    /**
     * 上传文件，可以批量上传，异步接口
     * <p>
     * m:遍历文件
     * <p>
     * mbPhoto:把图片的key（文件名）和图片地址持久化
     *
     * @param multipartFiles 文件
     * @param tid            帖子ID
     */
    @AOPLog("图片上传至七牛云")
    @Override
    public boolean addPictureInPost(MultipartFile[] multipartFiles, Long tid) throws IOException {
        int result = 0;
        for (MultipartFile m : multipartFiles) {
            String simpleUUID = IdUtil.fastSimpleUUID();
            qn.asyncUpload(this.ak, this.sk, this.bucket, simpleUUID, m.getBytes());
            MbPhoto mbPhoto = new MbPhoto()
                    .setLink(this.link + simpleUUID)
                    .setType(PostType.PHOTO_POST.getType())
                    .setTid(tid);
            result = mbPhotoMapper.insert(mbPhoto);
        }
        return result > 0;
    }

    /**
     * 修改数据
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    @Override
    public MbPost update(MbPost mbPost) {
        this.mbPostMapper.update(mbPost);
        return this.queryById(mbPost.getTid());
    }

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    @Override
    public MbPost queryById(Long tid) {
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long tid) {
        return this.mbPostMapper.deleteById(tid) > 0;
    }
}