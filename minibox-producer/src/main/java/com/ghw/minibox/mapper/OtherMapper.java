package com.ghw.minibox.mapper;

import com.ghw.minibox.dto.AllDataDto;

import java.util.List;

/**
 * @author Violet
 * @description 特殊需求mapper
 * @date 2021/1/4
 */

public interface OtherMapper {

    /**
     * 数帖子评论数
     *
     * @param postId 帖子的ID
     * @return 评论数
     */
    List<AllDataDto> countComment(List<Long> postId);

}
