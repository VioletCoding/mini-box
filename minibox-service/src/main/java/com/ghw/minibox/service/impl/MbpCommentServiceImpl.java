package com.ghw.minibox.service.impl;

import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpCommentMapper;
import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.service.BaseService;
import com.ghw.minibox.utils.DefaultColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/2/2
 */
@Service
public class MbpCommentServiceImpl implements BaseService<CommentModel> {
    @Resource
    private MbpCommentMapper mbpCommentMapper;

    @Override
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

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean modify(CommentModel model) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public CommentModel findOneById(Long id) {
        return null;
    }

    @Override
    public CommentModel findOne(String column, Object value) {
        return null;
    }

    @Override
    public List<CommentModel> findByModel(CommentModel model) {
        return null;
    }
}
