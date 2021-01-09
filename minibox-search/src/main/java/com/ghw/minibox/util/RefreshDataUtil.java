package com.ghw.minibox.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.repository.GameSearchRepository;
import com.ghw.minibox.repository.PostSearchRepository;
import com.qiniu.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 刷新数据工具
 * @date 2021/1/9
 */
@Component
public class RefreshDataUtil {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PostSearchRepository postSearchRepository;
    @Resource
    private GameSearchRepository gameSearchRepository;

    /**
     * 刷新数据
     */
    public void refresh() {
        String posts = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX);
        String games = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.GAME_PREFIX);
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            if (!StringUtils.isNullOrEmpty(posts)) {
                List<MbPost> postList = objectMapper.readValue(posts, new TypeReference<List<MbPost>>() {
                });
                if (postList.size() > 0) {
                    postSearchRepository.deleteAll();
                    postSearchRepository.saveAll(postList);
                }
            }


            if (!StringUtils.isNullOrEmpty(games)) {
                List<MbGame> gameList = objectMapper.readValue(games, new TypeReference<List<MbGame>>() {
                });
                if (gameList.size() > 0) {
                    gameSearchRepository.deleteAll();
                    gameSearchRepository.saveAll(gameList);
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
