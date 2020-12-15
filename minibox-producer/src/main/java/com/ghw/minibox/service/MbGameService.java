package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbGame;

import java.util.List;

/**
 * (MbGame)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:12
 */
public interface MbGameService {

    /**
     * 游戏库 -> 游戏列表
     *
     * @return 游戏列表
     */
    List<MbGame> showGameList();

    /**
     * 游戏库 -> 游戏详情
     *
     * @param gid 游戏gid
     * @return 游戏详情
     */
    MbGame showGameDetail(Long gid);

}