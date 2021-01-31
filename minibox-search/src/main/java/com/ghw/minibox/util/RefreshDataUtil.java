package com.ghw.minibox.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.es.ESMbGame;
import com.ghw.minibox.es.ESMbPost;
import com.ghw.minibox.feign.SearchFeignClient;
import com.ghw.minibox.repository.GameSearchRepository;
import com.ghw.minibox.repository.PostSearchRepository;
import com.ghw.minibox.component.GenerateBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 刷新数据工具
 * @date 2021/1/9
 */
@Component
@Slf4j
public class RefreshDataUtil {
    @Resource
    private GenerateBean generateBean;
    @Resource
    private PostSearchRepository postSearchRepository;
    @Resource
    private GameSearchRepository gameSearchRepository;
    @Resource
    private SearchFeignClient searchFeignClient;

    public void refresh() {
        log.info("开始刷新ES数据...");
        try {
            postSearchRepository.deleteAll();
            gameSearchRepository.deleteAll();

            Object posts = searchFeignClient.getDataFromService().getData();
            Object games = searchFeignClient.getDataFromServiceGame().getData();

            ObjectMapper objectMapper = generateBean.getObjectMapper();
            List<ESMbPost> mbPosts = objectMapper.convertValue(posts, new TypeReference<List<ESMbPost>>() {
            });
            postSearchRepository.saveAll(mbPosts);
            log.info("帖子的数据已更新完成!");
            List<ESMbGame> mbGames = objectMapper.convertValue(games, new TypeReference<List<ESMbGame>>() {
            });
            gameSearchRepository.saveAll(mbGames);
            log.info("游戏数据已更新完成!");

            log.info("刷新ES数据完成");
        } catch (Exception e) {
            log.error("刷新ES数据出现异常=>{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
