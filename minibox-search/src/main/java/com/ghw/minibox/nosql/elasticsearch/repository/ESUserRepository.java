package com.ghw.minibox.nosql.elasticsearch.repository;

import com.ghw.minibox.document.ESUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Violet
 * @description 用户ES操作类
 * @date 2020/11/19
 */

public interface ESUserRepository extends ElasticsearchRepository<ESUser, Long> {
    Page<ESUser> findByNickname(String nickname, Pageable pageable);
}
