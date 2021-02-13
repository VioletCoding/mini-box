package com.ghw.minibox.es;

import com.ghw.minibox.model.PostModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Violet
 * @description 帖子 搜索
 * @date 2021/2/13
 */

public interface PostSearch extends ElasticsearchRepository<PostModel, Long> {
    List<PostModel> findPostModelByTitleOrContent(String title, String content);
}
