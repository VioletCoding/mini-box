package com.ghw.minibox.repository;

import com.ghw.minibox.entity.MbPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Violet
 * @description 基础ElasticsearchRepository得到基础的操作ES的模板方法
 * @date 2021/1/4
 */

public interface PostSearchRepository extends ElasticsearchRepository<MbPost, Long> {
    Page<MbPost> findByTitleOrContent(String title, String content, Pageable pageable);
}
