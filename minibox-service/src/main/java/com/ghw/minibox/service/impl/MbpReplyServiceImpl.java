package com.ghw.minibox.service.impl;

import com.ghw.minibox.mapper.MbpReplyMapper;
import com.ghw.minibox.model.ReplyModel;
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
public class MbpReplyServiceImpl implements BaseService<ReplyModel> {

    @Resource
    private MbpReplyMapper mbpReplyMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(ReplyModel model) {
        model.setState(DefaultColumn.STATE.getMessage());
        return mbpReplyMapper.insert(model) > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean modify(ReplyModel model) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public ReplyModel findOneById(Long id) {
        return null;
    }

    @Override
    public ReplyModel findOne(String column, Object value) {
        return null;
    }

    @Override
    public List<ReplyModel> findByModel(ReplyModel model) {
        return null;
    }
}
