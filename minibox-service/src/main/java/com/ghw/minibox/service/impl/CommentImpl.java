package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.mapper.MbCommentMapper;
import com.ghw.minibox.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@Service
public class CommentImpl implements CommonService<MbComment> {

    @Resource
    private MbCommentMapper commentMapper;

    @Override
    public List<MbComment> selectAll(MbComment param) {
        return null;
    }

    @Override
    public MbComment selectOne(Long id) {
        return null;
    }

    @Override
    public boolean insert(MbComment entity) {
        return false;
    }

    @Override
    public boolean update(MbComment entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
