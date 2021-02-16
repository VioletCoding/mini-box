package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.mapper.MbpPostMapper;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.PostModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 搜索业务类
 * @date 2021/2/15
 */
@Service
public class MbpSearchService {

    @Resource
    private MbpPostMapper mbpPostMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;

    /**
     * 通过like 2个表组装数据获得搜索结果<br>
     * 本来用es的，写都写完了发现数据同步很有问题，不搞了，麻烦。<br>
     *
     * @param keyword 关键字
     */
    public Map<String, Object> search(String keyword) {
        //1.帖子
        QueryWrapper<PostModel> postWrapper = new QueryWrapper<>();
        postWrapper.select("id", "title", "photo_link", "create_date");
        postWrapper.like("title", keyword);
        List<PostModel> posts = mbpPostMapper.selectList(postWrapper);
        //2.游戏
        QueryWrapper<GameModel> gameWrapper = new QueryWrapper<>();
        gameWrapper.select("id", "name", "price", "origin_price", "photo_link");
        gameWrapper.like("name", keyword);
        List<GameModel> games = mbpGameMapper.selectList(gameWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("post", posts);
        map.put("game", games);
        return map;
    }

}
