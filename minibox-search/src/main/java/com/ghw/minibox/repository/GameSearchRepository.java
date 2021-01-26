package com.ghw.minibox.repository;

import com.ghw.minibox.es.ESMbGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Violet
 * @description 游戏搜索
 * @date 2021/1/8
 */

public interface GameSearchRepository extends ElasticsearchRepository<ESMbGame, Long> {
    Page<ESMbGame> findByName(String name, Pageable pageable);
}
