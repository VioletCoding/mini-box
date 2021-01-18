package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbPost;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 辅助工具类Mapper
 * @date 2021/1/4
 */

public interface MapperUtils {

    /**
     * 统计游戏信息、数量
     *
     * @param id 用户id
     * @return 游戏信息、数量
     */
    @MapKey("id")
    List<Map<String, Object>> countGameNum(Long id);

    /**
     * 用户个人信息显示自己的评论，以及评论在哪个帖子下发布的
     *
     * @param uid 用户id
     * @return 评论列表
     */
    List<MbPost> queryUserAllCommentInPost(Long uid);

    /**
     * 帖子详情内的所有信息 复杂查询
     *
     * @param id 帖子id
     * @return 帖子详情
     */
    List<MbPost> queryPost(Long id);

    /**
     * 赋予用户角色
     */
    int setUserRole(Long roleId, Long userId);

    /*
     * @author：Violet
     * @description：管理员页面首页的4个卡片的统计数值
     * @params:
     * @return 统计数值
     * @date：2021/1/15 16:37
     */
    Map<String, Object> count();

    /*
     * @author：Violet
     * @description：每日发帖数情况一览
     * @params:
     * @return 集合
     * @date：2021/1/15 20:35
     */
    List<Map<String, Object>> echartsPostPerDay();

    /*
     * @author：Violet
     * @description：每日评论数情况一览
     * @params:
     * @return 集合
     * @date：2021/1/18 14:32
     */
    List<Map<String,Object>> echartsCommentPerDay();

    /*
     * @author：Violet
     * @description：游戏销量排行榜
     * @params:
     * @return 集合
     * @date：2021/1/15 21:41
     */
    List<Map<String, Object>> gameSalesRankings();


}
