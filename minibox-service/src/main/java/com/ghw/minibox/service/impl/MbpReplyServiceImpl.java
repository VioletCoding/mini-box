package com.ghw.minibox.service.impl;

import com.ghw.minibox.mapper.MbpReplyMapper;
import com.ghw.minibox.model.ReplyModel;
import com.ghw.minibox.utils.DefaultColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 回复 业务逻辑层
 * @date 2021/2/2
 */
@Service
public class MbpReplyServiceImpl {

    @Resource
    private MbpReplyMapper mbpReplyMapper;

    /**
     * 发表回复
     *
     * @param model 实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(ReplyModel model) {
        model.setState(DefaultColumn.STATE.getMessage());
        return mbpReplyMapper.insert(model) > 0;
    }
}
