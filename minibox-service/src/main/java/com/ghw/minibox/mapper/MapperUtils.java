package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbPost;

import java.util.List;

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



}