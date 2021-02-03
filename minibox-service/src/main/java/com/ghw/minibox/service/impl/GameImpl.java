package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.*;
import com.ghw.minibox.mapper.*;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AopLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/1/5
 */
@Service
@Deprecated
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

    @AopLog("游戏列表")
    @Override
    public List<MbGame> selectAll(MbGame param) {
        return gameMapper.queryAll(param);
    }

    @AopLog("游戏详情")
    @Override
    public MbGame selectOne(Long id) {
        //游戏详情
        MbGame mbGame = gameMapper.queryById(id);
        //游戏图片列表
        List<MbPhoto> photos = photoMapper.queryAll(new MbPhoto().setGid(mbGame.getId()));
        //游戏下的评论列表
        List<MbComment> comments = commentMapper.queryAll(new MbComment().setGid(mbGame.getId()));
        //评论的用户
        List<Long> uIdList = new ArrayList<>();
        comments.forEach(comment -> uIdList.add(comment.getUid()));
        //游戏标签
        List<MbTag> tags = tagMapper.queryAll(new MbTag().setGid(mbGame.getId()));
        //数据组装
        if (uIdList.size() > 0) {
            List<MbUser> users = userMapper.queryInId(uIdList);
            comments.forEach(comment -> users.forEach(user -> {
                if (comment.getUid().equals(user.getId())) comment.setMbUser(user);
            }));
        }
        mbGame.setPhotoList(photos);
        mbGame.setTagList(tags);
        mbGame.setCommentList(comments);
        return mbGame;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean insert(MbGame entity) {
        int insert = gameMapper.insert(entity);
        return insert > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean update(MbGame entity) {
        int update = gameMapper.update(entity);
        return update > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean delete(Long id) {
        return gameMapper.deleteById(id) > 0;
    }
}
