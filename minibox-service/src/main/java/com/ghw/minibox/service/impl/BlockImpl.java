package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.mapper.MbBlockMapper;
import com.ghw.minibox.mapper.MbGameMapper;
import com.ghw.minibox.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@Service
public class BlockImpl implements CommonService<MbBlock> {
    @Resource
    private MbBlockMapper blockMapper;
    @Resource
    private MbGameMapper gameMapper;

    @Override
    public List<MbBlock> selectAll(MbBlock param) {

        List<MbBlock> blocks = blockMapper.queryAll(null);
        //获取游戏的图片
        List<Long> gidList = new ArrayList<>();
        blocks.forEach(block -> gidList.add(block.getGid()));
        List<MbGame> games = new ArrayList<>();

        if (gidList.size() > 0)
            games = gameMapper.queryInId(gidList);

        //数据组装
        List<MbGame> finalGames = games;
        blocks.forEach(block -> finalGames.forEach(game -> {
            if (game.getId().equals(block.getGid()))
                block.setMbGame(game);
        }));
        return blocks;
    }

    @Override
    public MbBlock selectOne(Long id) {
        return null;
    }

    @Override
    public Object insert(MbBlock entity) {
        return false;
    }

    @Override
    public boolean update(MbBlock entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
