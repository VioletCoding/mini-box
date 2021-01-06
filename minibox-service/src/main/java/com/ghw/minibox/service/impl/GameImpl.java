package com.ghw.minibox.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.*;
import com.ghw.minibox.mapper.*;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.GenerateBean;
import com.qiniu.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/1/5
 */
@Service
public class GameImpl implements CommonService<MbGame> {

    @Resource
    private MbGameMapper gameMapper;
    @Resource
    private MbCommentMapper commentMapper;
    @Resource
    private MbPhotoMapper photoMapper;
    @Resource
    private MbUserMapper userMapper;
    @Resource
    private MbTagMapper tagMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private GenerateBean generateBean;

    @AOPLog("游戏列表")
    @Override
    public List<MbGame> selectAll(MbGame param) throws JsonProcessingException {
        ObjectMapper objectMapper = generateBean.getObjectMapper();
        //先查缓存
        String fromRedis = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.GAME_PREFIX);
        if (!StringUtils.isNullOrEmpty(fromRedis)) {
            return objectMapper.readValue(fromRedis, new TypeReference<List<MbGame>>() {
            });
        }

        List<MbGame> games = gameMapper.queryAll(param);
        //打进缓存
        redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.GAME_PREFIX, objectMapper.writeValueAsString(games), 86400L);
        return games;
    }

    @AOPLog("游戏详情")
    @Override
    public MbGame selectOne(Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = generateBean.getObjectMapper();
        //先查缓存
        String fromRedis = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.GAME_PREFIX + id);
        if (!StringUtils.isNullOrEmpty(fromRedis)) {
            return objectMapper.readValue(fromRedis, MbGame.class);
        }

        //游戏详情
        MbGame mbGame = gameMapper.queryById(id);
        //游戏图片列表
        List<MbPhoto> photos = photoMapper.queryAll(new MbPhoto().setGid(mbGame.getId()));
        //游戏下的评论列表
        List<MbComment> comments = commentMapper.queryAll(new MbComment().setGid(mbGame.getId()));
        //评论的用户
        List<Long> uIdList = new ArrayList<>();
        comments.forEach(comment -> {
            uIdList.add(comment.getUid());
        });
        //游戏标签
        List<MbTag> tags = tagMapper.queryAll(new MbTag().setGid(mbGame.getId()));
        //数据组装
        if (uIdList.size() > 0) {
            List<MbUser> users = userMapper.queryInId(uIdList);
            comments.forEach(comment -> users.forEach(user -> {
                if (comment.getUid().equals(user.getId())) {
                    comment.setMbUser(user);
                }
            }));
        }
        mbGame.setPhotoList(photos);
        mbGame.setTagList(tags);
        mbGame.setCommentList(comments);
        //打进缓存
        redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.GAME_PREFIX + id, objectMapper.writeValueAsString(mbGame), 86400L);
        return mbGame;
    }

    @Override
    public boolean insert(MbGame entity) {
        return false;
    }

    @Override
    public boolean update(MbGame entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
