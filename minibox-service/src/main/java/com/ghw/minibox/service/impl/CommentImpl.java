package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbReply;
import com.ghw.minibox.exception.MyException;
import com.ghw.minibox.mapper.MbCommentMapper;
import com.ghw.minibox.mapper.MbReplyMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.PostType;
import com.ghw.minibox.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@Service
@Slf4j
public class CommentImpl implements CommonService<MbComment> {

    @Resource
    private MbCommentMapper commentMapper;
    @Resource
    private MbReplyMapper replyMapper;

    @Override
    public List<MbComment> selectAll(MbComment param) {
        return null;
    }

    @Override
    public MbComment selectOne(Long id) {
        return null;
    }


    @AOPLog("发表评论")
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Object insert(MbComment entity){

        boolean isPost = entity.getType().equals(PostType.COMMENT_IN_POST.getType());
        if (isPost)
            if (entity.getTid() == null)
                throw new MyException(ResultCode.TID_IS_NULL.getMessage());

        boolean isGame = entity.getType().equals(PostType.COMMENT_IN_GAME.getType());
        if (isGame)
            if (entity.getGid() == null)
                throw new MyException(ResultCode.GID_IS_NULL.getMessage());

        int insert = commentMapper.insert(entity);
        return insert > 0;
    }

    @AOPLog("发表回复")
    @Transactional(rollbackFor = Throwable.class)
    public boolean Reply(MbReply mbReply) {

        if (mbReply.getType().equals(PostType.REPLY_IN_POST.getType())) {
            if (mbReply.getReplyInPost() == null)
                throw new RuntimeException("当type等于TR时，replyInPost字段不能为空");
            mbReply.setReplyInGame(null);
        }

        if (mbReply.getType().equals(PostType.REPLY_IN_GAME.getType())) {
            if (mbReply.getReplyInGame() == null)
                throw new RuntimeException("当type等于GR时，replyInGame字段不能为空");
            mbReply.setReplyInPost(null);
        }

        int insert = replyMapper.insert(mbReply);

        return insert > 0;
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
