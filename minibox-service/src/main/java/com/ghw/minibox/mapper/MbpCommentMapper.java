package com.ghw.minibox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghw.minibox.model.CommentModel;

import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/2/2
 */

public interface MbpCommentMapper extends BaseMapper<CommentModel> {
    /**
     * 查找帖子详情里，关于 评论、评论人、回复、回复人，连表查询
     * @param postId
     * @return
     */
    List<CommentModel> findCommentAndReplyByPostId(Long postId);
}
