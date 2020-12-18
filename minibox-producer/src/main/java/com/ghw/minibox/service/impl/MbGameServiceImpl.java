package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.mapper.MbGameMapper;
import com.ghw.minibox.service.MbGameService;
import com.ghw.minibox.utils.AOPLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbGame)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:12
 */
@Service
public class MbGameServiceImpl implements MbGameService {
    @Resource
    private MbGameMapper mbGameMapper;

    /**
     * 游戏库 -> 游戏列表
     *
     * @return 游戏列表
     */
    @AOPLog("游戏列表")
    @Override
    public List<MbGame> showGameList() {
        return mbGameMapper.queryAll();
    }

    /**
     * 游戏库 -> 游戏详情
     *
     * @param gid 游戏gid
     * @return 游戏详情
     */
    @AOPLog("游戏详情")
    @Override
    public MbGame showGameDetail(Long gid) {
        //TODO
        //return mbGameMapper.queryById(gid);
        return null;
    }
}