package com.ghw.minibox.es;

import com.ghw.minibox.model.GameModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Violet
 * @description 游戏 搜索
 * @date 2021/2/13
 */

public interface GameSearch extends ElasticsearchRepository<GameModel, Long> {
    List<GameModel> findGameModelByNameOrDeveloperOrPublisher(String name, String dev, String pub);
}
