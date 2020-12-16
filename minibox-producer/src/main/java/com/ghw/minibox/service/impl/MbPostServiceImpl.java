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
    private MbUserMapper mbUserMapper;
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
    @AOPLog("首页帖子列表")
    @Override
    public List<MbPost> showPostList() {
        //TODO
        //for (MbPost m : showPostList) {
        //    List<MbPhoto> photoList = m.getPhotoList();
        //    for (MbPhoto p : photoList) {
        //        m.setHeadPhotoLink(p.getPhotoLink());
        //    }
        //}
        return mbPostMapper.showPostList();
    }


    /**
     * 展示帖子详情
     * 获取帖子里任意一张图片作为封面图
     *
     * @param tid 主键
     * @return 帖子详情，帖子作者，评论列表，评论谁发的
     */
    @Override
    public MbPost showPostDetail(Long tid) {
        MbPost mbPost = mbPostMapper.showPostDetail(tid);
        if (mbPost.getPhotoList() != null) {
            List<MbPhoto> photoList = mbPost.getPhotoList();
            for (MbPhoto p : photoList) {
                mbPost.setHeadPhotoLink(p.getPhotoLink());
                break;
            }
        }
        return mbPost;
    }

    /**
     * 发表帖子
     *
     * @param mbPost 实例对象
     * @return 统一结果
     */
    @AOPLog("发布帖子")
    @Override
    public ReturnDto<ResultCode> publish(MbPost mbPost) {
        MbUser mbUser = mbUserMapper.queryById(mbPost.getUid());
        if (mbUser == null) {
            return gr.custom(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage());
        }
        int result = mbPostMapper.insert(mbPost);

        if (result > 0) {
            return gr.success();
        }
        return gr.fail();
    }

    /**
     * 上传文件，可以批量上传，但是七牛云本身是不支持批量上传的，所以只能在循环中遍历上传接口，该上传接口是异步接口asyncUpload()
     * m:遍历文件
     * img:文件的名称和链接，用于返回前端作为图片回显
     * photoList:用于mybatis批量插入
     * mbPhoto:把图片的key（文件名）和图片地址持久化
     *
     * @param multipartFiles 文件，支持多个
     */
    @AOPLog("图片上传至七牛云")
    @Override
    public List<String> addPictureInPost(MultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles.length <= 0) {
            return null;
        }

        //用于批量返回图片的链接
        List<String> img = new ArrayList<>();
        //用于批量插入数据库
        List<MbPhoto> photoList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String simpleUUID = IdUtil.fastSimpleUUID();
            qn.asyncUpload(this.ak, this.sk, this.bucket, simpleUUID, multipartFile.getInputStream());
            log.info("检查手机文件上传，尝试获取文件名==>{}", multipartFile.getOriginalFilename());
            MbPhoto mbPhoto = new MbPhoto()
                    .setPhotoLink(this.link + simpleUUID)
                    .setType(PostType.PHOTO_POST.getType());

            photoList.add(mbPhoto);
            img.add(this.link + simpleUUID);
        }
        //批量插入
        int insertBatch = mbPhotoMapper.insertBatch(photoList);

        if (insertBatch > 0) {
            return img;
        }

        return null;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    @Override
    public MbPost queryById(Long tid) {
        //TODO
        return null;
    }

}