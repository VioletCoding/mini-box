package com.ghw.minibox.service.impl;

import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpCommentMapper;
import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.utils.DefaultColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 评论 业务逻辑层
 * @date 2021/2/2
 */
@Service
public class MbpCommentService {
    @Resource
    private MbpCommentMapper mbpCommentMapper;

    /**
     * 发表评论
     *
     * @param model 实体
     * @return 是否成功
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(CommentModel model) {
        if (model.getPostId() == null && model.getGameId() == null) {
            throw new MiniBoxException("帖子ID或游戏ID为空");
        }
        if (model.getGameId() != null) {
            if (model.getScore() == null) {
                throw new MiniBoxException("游戏评分不能为空");
            }
            model.setPostId(null);
        }
        model.setState(DefaultColumn.STATE.getMessage());
        return mbpCommentMapper.insert(model) > 0;
    }

}
