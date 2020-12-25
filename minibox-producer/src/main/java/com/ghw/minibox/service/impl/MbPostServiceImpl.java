package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.dto.ReturnImgDto;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.mapper.MbPostMapper;
import com.ghw.minibox.service.MbPostService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.PostType;
import com.ghw.minibox.utils.QiNiuUtil;
import com.ghw.minibox.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
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
    private MbPhotoMapper mbPhotoMapper;
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
     * 在首页显示帖子列表
     * 循环遍历帖子的图片，获取头图的第一张图片的链接作为头图
     *
     * @return 分页过后的帖子列表
     */
    @AOPLog("展示首页帖子列表")
    @Cacheable(RedisUtil.POST_CACHE)
    @Override
    public List<MbPost> showPostList(MbPost mbPost) {
        return mbPostMapper.queryAll(mbPost);
    }


    /**
     * 显示用户的所有评论在哪些帖子
     *
     * @param uid 用户id
     * @return 信息
     */
    @AOPLog("展示某一用户的评论-关联帖子信息")
    @Override
    public List<MbPost> showPostInUser(Long uid) {
        return mbPostMapper.queryUserAllCommentInPost(uid);
    }

    /**
     * 展示帖子详情
     * 获取帖子里任意一张图片作为封面图
     *
     * @param tid 主键
     * @return 帖子详情，帖子作者，评论列表，评论谁发的
     */
    @AOPLog("展示帖子详情")
    @Override
    public List<MbPost> showPostDetail(Long tid) {
        return mbPostMapper.queryAll(new MbPost().setTid(tid));
    }

    /**
     * 发表帖子
     *
     * @param mbPost 实例对象
     * @return 统一结果
     */
    @AOPLog("发布帖子")
    //@CachePut(RedisConfig.REDIS_KEY_DATABASE)
    @Override
    public ReturnDto<ResultCode> publish(MbPost mbPost) {
        //帖子封面图
        String coverImg;
        Long pid = null;
        if (mbPost != null) {
            coverImg = mbPost.getCoverImg();
            int insert = mbPostMapper.insert(mbPost);
            //帖子封面图判空
            if (mbPost.getCoverImg() != null && !mbPost.getCoverImg().equals("")) {
                if (mbPost.getMbPhoto().getPid() != null) {
                    //获取封面图的图片ID，用于关联帖子，知道是哪个帖子的封面图
                    pid = mbPost.getMbPhoto().getPid();
                }
            }
            //把封面图和帖子关联起来
            int update = mbPhotoMapper.update(new MbPhoto().setPid(pid).setTid(mbPost.getTid()));

            if (insert > 0) {
                return gr.success();
            }
            return gr.fail();
        }
        return null;
    }


    /**
     * 上传文件，可以批量上传，但是七牛云本身是不支持批量上传的，所以只能在循环中遍历上传接口，该上传接口是异步接口asyncUpload()
     *
     * @param multipartFiles 文件，支持多个
     */
    @AOPLog("图片上传至七牛云")
    @Override
    public ReturnImgDto addPictureInPost(MultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles.length < 1) {
            return null;
        }
        //用于返回自增ID和图片的完整外链
        ReturnImgDto dto = new ReturnImgDto();
        //文件名
        String simpleUUID;
        MbPhoto mbPhoto;
        int insert = 0;
        List<Long> photoIdList = new ArrayList<>();
        List<String> photoImgList = new ArrayList<>();
        //遍历文件
        for (MultipartFile m : multipartFiles) {
            simpleUUID = IdUtil.fastSimpleUUID();
            //使用字节数组上传，可以获取上传进度
            qn.asyncUpload(this.ak, this.sk, this.bucket, simpleUUID, m.getInputStream());
            //保存图片信息到数据库
            mbPhoto = new MbPhoto()
                    .setPhotoLink(this.link + simpleUUID)
                    .setType(PostType.PHOTO_POST.getType());
            insert = mbPhotoMapper.insert(mbPhoto);

            //单个文件
            if (multipartFiles.length == 1) {
                dto.setPhotoId(mbPhoto.getPid());
                dto.setPhotoImg(this.link + simpleUUID);
            } else {
                //多个文件
                photoIdList.add(mbPhoto.getPid());
                dto.setPhotoIdList(photoIdList);
                photoImgList.add(this.link + simpleUUID);
                dto.setPhotoImgList(photoImgList);
            }
        }

        if (insert > 0) {
            return dto;
        }
        return null;
    }

}