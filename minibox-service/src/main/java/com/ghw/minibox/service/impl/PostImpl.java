package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.*;
import com.ghw.minibox.mapper.*;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.QiNiuUtil;
import com.qiniu.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 帖子实现
 * @date 2021/1/4
 */
@Service
@Slf4j
public class PostImpl implements CommonService<MbPost> {

    @Resource
    private MbPostMapper postMapper;
    @Resource
    private MapperUtils mapperUtils;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private QiNiuUtil qiNiuUtil;
    @Value("${qiNiu.link}")
    private String qnLink;


    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    /**
     * 帖子列表
     *
     * @param param 参数
     * @return 帖子列表
     */
    @AOPLog("帖子列表")
    @Override
    public List<MbPost> selectAll(MbPost param) throws JsonProcessingException {
        ObjectMapper objectMapper = this.getObjectMapper();

        //先查redis
        String fromRedis = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX);
        if (!StringUtils.isNullOrEmpty(fromRedis)) {
            return objectMapper.readValue(fromRedis, new TypeReference<List<MbPost>>() {
            });
        }

        List<MbPost> posts = mapperUtils.queryPost(null);
        //打进缓存
        String json = objectMapper.writeValueAsString(posts);
        redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX, json, 86400L);
        return posts;
    }


    @AOPLog("用户个人信息显示自己的评论，以及评论在哪个帖子下发布的")
    public Object getCommentAndPostByUid(Long uid) {
        return mapperUtils.queryUserAllCommentInPost(uid);
    }

    /**
     * 发布帖子
     *
     * @param entity 实体
     * @return 是否成功
     */
    @AOPLog("发布帖子")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(MbPost entity) throws JsonProcessingException {
        int insert = postMapper.insert(entity);
        if (insert > 0) {
            //更新缓存
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX);
            return true;
        }
        return false;
    }

    /**
     * 七牛云文件上传，可以批量上传
     *
     * @param multipartFiles 多部件接口
     * @return map集合，里面有图片的全部链接
     * @throws IOException -
     */
    @AOPLog("文件上传")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> upload(MultipartFile[] multipartFiles) throws IOException {

        if (multipartFiles.length < 1) {
            throw new RuntimeException("文件为空");
        }

        List<String> links = new ArrayList<>();

        for (MultipartFile m : multipartFiles) {
            String fastSimpleUUID = IdUtil.fastSimpleUUID();
            //key是文件名，随机生成，使用字节数组上传可以获取上传进度，以及断点续传
            qiNiuUtil.syncUpload(fastSimpleUUID, m.getBytes());
            links.add(this.qnLink + fastSimpleUUID);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("images", links);
        return map;
    }

    /**
     * 展示帖子详情
     *
     * @param id 帖子id
     * @return 帖子详情
     */
    @AOPLog("帖子详情")
    @Override
    public MbPost selectOne(Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapper();
        //先查缓存
        String fromRedis = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX + id);
        if (!StringUtils.isNullOrEmpty(fromRedis)){
            return objectMapper.readValue(fromRedis,MbPost.class);
        }

        MbPost post = mapperUtils.queryPost(id).get(0);

        redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX + id, objectMapper.writeValueAsString(post), 86400L);
        return post;
    }

    @Override
    public boolean update(MbPost entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
