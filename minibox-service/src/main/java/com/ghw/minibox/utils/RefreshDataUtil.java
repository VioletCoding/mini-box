package com.ghw.minibox.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.feign.SearchFeignClient;
import com.ghw.minibox.service.impl.GameImpl;
import com.ghw.minibox.service.impl.PostImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 刷新缓存数据
 * @date 2021/1/9
 */
@Component
public class RefreshDataUtil {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PostImpl post;
    @Resource
    private GameImpl game;
    @Resource
    private SearchFeignClient searchFeignClient;


    public static final int POST = 0;
    public static final int GAME = 1;

    /**
     * 刷新数据
     *
     * @throws JsonProcessingException -
     */
    public void refresh(int refreshType) throws JsonProcessingException {

        if (refreshType == 0) {
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX);
            post.selectAll(null);
            searchFeignClient.refreshData();
        }

        if (refreshType == 1){
            redisUtil.remove(RedisUtil.REDIS_PREFIX+RedisUtil.GAME_PREFIX);
            game.selectAll(null);
            searchFeignClient.refreshData();
        }
    }
}
