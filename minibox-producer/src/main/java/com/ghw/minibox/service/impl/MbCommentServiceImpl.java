package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbReply;
import com.ghw.minibox.mapper.MbCommentMapper;
import com.ghw.minibox.service.MbCommentService;
import com.ghw.minibox.utils.PostType;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MbComment)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:11
 */
@Service
public class MbCommentServiceImpl implements MbCommentService {
    @Resource
    private MbCommentMapper mbCommentMapper;


    /**
     * 发表评论
     *
     * @param mbComment 实例对象
     * @return 实例对象
     */
    @Override
    public ResultCode postComment(MbComment mbComment) {
        if (mbComment.getType().equals(PostType.COMMENT_IN_POST.getType()) && mbComment.getTid() == null)
            return ResultCode.TID_IS_NULL;

        if (mbComment.getType().equals(PostType.COMMENT_IN_GAME.getType())) {
            if (mbComment.getGid() == null)
                return ResultCode.GID_IS_NULL;
            if (mbComment.getScore() == null)
                return ResultCode.SCORE_IS_NULL;
        }

        if (mbCommentMapper.insert(mbComment) > 0) {
            return ResultCode.OK;
        }
        return ResultCode.INTERNAL_SERVER_ERROR;
    }

    /**
     * 发表回复
     * 当type=TR,replyInPost不能为空
     * 当type=GR,replyInGame不能为空
     * 其余校验通过lombok
     *
     * @param mbReply 实体
     * @return 枚举响应体，成功返回OK
     */
    @Override
    public ResultCode postReply(MbReply mbReply) {

        if (mbReply.getType().equals(PostType.REPLY_IN_POST.getType())) {
            if (mbReply.getReplyInPost() == null)
                return ResultCode.REPLY_IN_POST_IS_NULL;
            mbReply.setReplyInGame(null);
        }
        if (mbReply.getType().equals(PostType.REPLY_IN_GAME.getType())) {
            if (mbReply.getReplyInGame() == null)
                return ResultCode.REPLY_IN_GAME_IS_NULL;
            mbReply.setReplyInPost(null);
        }

        int result = mbCommentMapper.insertToMbReply(mbReply);

        if (result > 0) {
            return ResultCode.OK;
        }
        return ResultCode.INTERNAL_SERVER_ERROR;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param cid 主键
     * @return 实例对象
     */
    @Override
    public MbComment queryById(Long cid) {
        return this.mbCommentMapper.queryById(cid);
    }


    /**
     * 修改数据
     *
     * @param mbComment 实例对象
     * @return 实例对象
     */
    @Override
    public MbComment update(MbComment mbComment) {
        this.mbCommentMapper.update(mbComment);
        return this.queryById(mbComment.getCid());
    }

    /**
     * 通过主键删除数据
     *
     * @param cid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long cid) {
        return this.mbCommentMapper.deleteById(cid) > 0;
    }
}