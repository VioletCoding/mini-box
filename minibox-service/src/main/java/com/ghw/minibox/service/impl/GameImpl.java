package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.mapper.MbCommentMapper;
import com.ghw.minibox.mapper.MbGameMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @AOPLog("游戏列表")
    @Override
    public List<MbGame> selectAll(MbGame param) {
        return gameMapper.queryAll(param);
    }

    @AOPLog("游戏详情")
    @Override
    public MbGame selectOne(Long id) {
        MbGame mbGame = gameMapper.queryById(id);
        //游戏下的评论列表
        List<MbComment> comments = commentMapper.queryAll(new MbComment().setGid(mbGame.getId()));
        mbGame.setCommentList(comments);
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
