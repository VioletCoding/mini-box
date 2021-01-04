package com.ghw.minibox.repository;

import com.ghw.minibox.entity.MbPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Violet
 * @description 帖子搜索
 * @date 2021/1/4
 */

public interface PostSearchRepository extends ElasticsearchRepository<MbPost, Long> {
    Page<MbPost> findByTitle(String title, Pageable pageable);
}
