package com.ghw.minibox.service.impl;

import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbReply;
import com.ghw.minibox.mapper.MbCommentMapper;
import com.ghw.minibox.mapper.MbReplyMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.PostType;
import com.ghw.minibox.utils.ResultCode;
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
public class CommentImpl implements CommonService<MbComment> {

    @Resource
    private MbCommentMapper commentMapper;
    @Resource
    private MbReplyMapper replyMapper;
    @Resource
    private RedisUtil redisUtil;

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
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(MbComment entity) {

        if (entity.getType().equals(PostType.COMMENT_IN_POST.getType())) {
            if (entity.getTid() == null) {
                throw new RuntimeException(ResultCode.TID_IS_NULL.getMessage());
            }
        }

        if (entity.getType().equals(PostType.COMMENT_IN_GAME.getType())) {
            if (entity.getGid() == null) {
                throw new RuntimeException(ResultCode.GID_IS_NULL.getMessage());
            }
        }

        int insert = commentMapper.insert(entity);
        if (insert > 0) {
            //更新缓存
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX);
            return true;
        }
        return false;
    }

    @AOPLog("发表回复")
    @Transactional(rollbackFor = Exception.class)
    public boolean Reply(MbReply mbReply) {

        if (mbReply.getType().equals(PostType.REPLY_IN_POST.getType())) {
            if (mbReply.getReplyInPost() == null) {
                throw new RuntimeException("当type等于TR时，replyInPost字段不能为空");
            }
            mbReply.setReplyInGame(null);
        }

        if (mbReply.getType().equals(PostType.REPLY_IN_GAME.getType())) {
            if (mbReply.getReplyInGame() == null) {
                throw new RuntimeException("当type等于GR时，replyInGame字段不能为空");
            }
            mbReply.setReplyInPost(null);
        }

        int insert = replyMapper.insert(mbReply);

        if (insert > 0) {
            //更新帖子详情缓存
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.POST_PREFIX + mbReply.getReplyInPost());
            return true;
        }
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
