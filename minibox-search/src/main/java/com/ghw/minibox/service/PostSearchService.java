package com.ghw.minibox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.entity.MbPost;
import org.springframework.data.domain.Page;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */

public interface PostSearchService {

    Page<MbPost> search(String title, String content,Integer pageNum, Integer pageSize);

    void getData() throws JsonProcessingException;
}
