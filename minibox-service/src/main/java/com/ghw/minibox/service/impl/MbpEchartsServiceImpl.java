package com.ghw.minibox.service.impl;

import com.ghw.minibox.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 管理员首页的echarts数据集合
 * @date 2021/2/5
 */
@Service
public class MbpEchartsServiceImpl {
    @Resource
    private MbpUserMapper mbpUserMapper;
    @Resource
    private MbpPostMapper mbpPostMapper;
    @Resource
    private MbpCommentMapper mbpCommentMapper;
    @Resource
    private MbpReplyMapper mbpReplyMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;
    @Resource
    private MbpUtilMapper mbpUtilMapper;

    public Integer postNumber() {
        return mbpPostMapper.selectCount(null);
    }

    public Integer userNumber() {
        return mbpUserMapper.selectCount(null);
    }

    public Integer gameNumber() {
        return mbpGameMapper.selectCount(null);
    }

    public Integer commentNumber() {
        Integer count = mbpReplyMapper.selectCount(null);
        return mbpCommentMapper.selectCount(null) + count;
    }

    /**
     * 构造echarts数据对象
     *
     * @return 数据对象
     */
    public Map<String, Object> build() {
        //包装器
        Map<String, Object> wrapper = new HashMap<>();
        //每日 评论数
        List<Map<String, Object>> commentPerDay = mbpUtilMapper.commentPerDay();
        //每日 发帖数
        List<Map<String, Object>> postPerDay = mbpUtilMapper.postPerDay();
        //游戏销量排行榜
        List<Map<String, Object>> gameSalesRankings = mbpUtilMapper.gameSalesRankings();
        //实例
        Map<String, Object> model = new HashMap<>();
        //总发帖数
        Integer postNumber = postNumber();
        //总用户数
        Integer userNumber = userNumber();
        //总游戏数
        Integer gameNumber = gameNumber();
        //总评论数
        Integer commentNumber = commentNumber();
        model.put("postNumber", postNumber);
        model.put("userNumber", userNumber);
        model.put("gameNumber", gameNumber);
        model.put("commentNumber", commentNumber);
        model.put("commentPerDay", commentPerDay);
        model.put("postPerDay", postPerDay);
        model.put("gameSalesRanking",gameSalesRankings);
        wrapper.put("echartsData", model);
        return wrapper;
    }

}
