package com.ghw.minibox.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.es.ESGameModel;
import com.ghw.minibox.es.ESPostModel;
import com.ghw.minibox.feign.SearchFeignClient;
import com.ghw.minibox.repository.GameSearchRepository;
import com.ghw.minibox.repository.PostSearchRepository;
import com.ghw.minibox.component.GenerateBean;
import com.ghw.minibox.vo.ResultVo;
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

            ResultVo postData = searchFeignClient.getPostData();
            ResultVo gameData = searchFeignClient.getGameData();

            ObjectMapper objectMapper = generateBean.getObjectMapper();
            List<ESPostModel> mbPosts = objectMapper.convertValue(postData.getData(), new TypeReference<List<ESPostModel>>() {
            });
            postSearchRepository.saveAll(mbPosts);
            log.info("帖子的数据已更新完成!");
            List<ESGameModel> mbGames = objectMapper.convertValue(gameData.getData(), new TypeReference<List<ESGameModel>>() {
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
