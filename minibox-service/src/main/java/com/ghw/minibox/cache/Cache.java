package com.ghw.minibox.cache;

import com.ghw.minibox.es.GameSearch;
import com.ghw.minibox.es.PostSearch;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.mapper.MbpPostMapper;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.PostModel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 启动后，将数据打入缓存
 * @date 2021/2/13
 */
@Component
public class Cache implements ApplicationRunner {
    @Resource
    private PostSearch postSearch;
    @Resource
    private GameSearch gameSearch;
    @Resource
    private MbpPostMapper mbpPostMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;

    @Override
    public void run(ApplicationArguments args) {
        //1.查数据库
        List<PostModel> postModels = mbpPostMapper.selectList(null);
        List<GameModel> gameModels = mbpGameMapper.selectList(null);
        //2.存储到ES
        postSearch.saveAll(postModels);
        gameSearch.saveAll(gameModels);
    }
}
