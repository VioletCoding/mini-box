package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbPost;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 辅助工具类Mapper
 * @date 2021/1/4
 */

public interface MapperUtils {

    /**
     * 数帖子评论数
     *
     * @param postId 帖子的ID
     * @return 评论数
     */
    List<MbPost> countComment(List<Long> postId);

    /**
     * 统计游戏信息、数量
     *
     * @param id 用户id
     * @return 游戏信息、数量
     */
    @MapKey("id")
    List<Map<String, Object>> countGameNum(Long id);


}
