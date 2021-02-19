package com.ghw.minibox.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 工具类mapper
 * @date 2021/2/5
 */

public interface MbpUtilMapper {
    /**
     * 每天的评论数
     */
    List<Map<String, Object>> commentPerDay();

    /**
     * 每天的帖子数
     */
    List<Map<String, Object>> postPerDay();

    /**
     * 游戏销量排行榜
     */
    List<Map<String, Object>> gameSalesRankings();

}
