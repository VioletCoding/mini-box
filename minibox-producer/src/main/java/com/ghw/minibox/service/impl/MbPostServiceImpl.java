package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.*;
import com.ghw.minibox.service.MbPostService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.PostType;
import com.ghw.minibox.utils.QiNiuUtil;
import com.ghw.minibox.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private MbBlockMapper mbBlockMapper;
    @Resource
    private MbUserMapper mbUserMapper;
    @Resource
    private OtherMapper otherMapper;
    @Resource
    private MbCommentMapper commentMapper;
    @Resource
    private GenerateResult<ResultCode> gr;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private QiNiuUtil qn;
    @Value("${qiNiu.link}")
    private String link;

    /**
     * @param mbPost 实例
     * @return 列表
     */
    @AOPLog("帖子列表")
    @Override
    public List<MbPost> showAll(MbPost mbPost) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //先从缓存拿
        String fromRedis = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX);

        if (fromRedis != null) {
            return objectMapper.readValue(fromRedis, new TypeReference<List<MbPost>>() {
            });
        }

        //获取帖子列表
        List<MbPost> postList = mbPostMapper.getAll(mbPost);
        //获取版块id集合
        List<Long> bidList = new ArrayList<>();
        //获取用户id集合
        List<Long> uidList = new ArrayList<>();
        //获取帖子id集合
        List<Long> tidList = new ArrayList<>();
        //把帖子列表中的bid字段加到list中
        postList.forEach(post -> {
            bidList.add(post.getBid());
            uidList.add(post.getUid());
            tidList.add(post.getTid());
        });

        //使用in查询，查出所有版块，顺序是固定的
        List<MbBlock> blocks = mbBlockMapper.queryInId(bidList);
        //使用in查询，查出所有用户，顺序是固定的
        List<MbUser> users = mbUserMapper.queryInId(uidList);
        //查询每个帖子的评论数，顺序是固定的
        List<MbPost> countComment = otherMapper.countComment(tidList);

        postList.forEach(post -> {
            //把所有的版块信息组装到MbPost对象中
            blocks.forEach(post::setMbBlock);
            //把所有的用户信息组装到MbPost对象中
            users.forEach(post::setMbUser);
            //把组装的评论数加到实例中
            countComment.forEach(c -> {
                if (c.getTid().equals(post.getTid())) {
                    post.setCountComment(c.getCountComment());
                } else {
                    post.setCountComment(0L);
                }
            });
        });
        String json = objectMapper.writeValueAsString(postList);
        redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX, json, 86400L);
        return postList;
    }


    /**
     * 发表新帖子
     *
     * @param mbPost 实例
     * @return 对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newPost(MbPost mbPost) throws JsonProcessingException {
        int insert = mbPostMapper.insert(mbPost);
        if (insert > 0) {
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX);
            //更新缓存
            List<MbPost> posts = this.showAll(null);
            String json = new ObjectMapper().writeValueAsString(posts);
            redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX, json, 86400L);
            return true;
        }
        return false;
    }

    /**
     * 上传文件，可以批量上传
     *
     * @param multipartFiles 文件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object addPictureInPost(MultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles.length < 1) {
            return null;
        }

        String simpleUUID;
        List<String> img = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            simpleUUID = IdUtil.fastSimpleUUID();
            qn.syncUpload(simpleUUID, multipartFile.getBytes());
            img.add(this.link + simpleUUID);
            MbPhoto mbPhoto = new MbPhoto().setPhotoLink(this.link + simpleUUID).setType(PostType.PHOTO_POST.getType());
            mbPhotoMapper.insert(mbPhoto);
        }
        return img;
    }


    //***********************************************分割线******************************


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


}