package com.ghw.minibox.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpCommentMapper;
import com.ghw.minibox.mapper.MbpPostMapper;
import com.ghw.minibox.mapper.MbpReplyMapper;
import com.ghw.minibox.mapper.MbpUserMapper;
import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.model.ReplyModel;
import com.ghw.minibox.model.UserModel;
import com.ghw.minibox.utils.DefaultColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 帖子 业务逻辑实现
 * @date 2021/2/1
 */
@Service
public class MbpPostServiceImpl {
    @Resource
    private MbpPostMapper mbpPostMapper;
    @Resource
    private MbpUserMapper mbpUserMapper;
    @Resource
    private MbpCommentMapper mbpCommentMapper;
    @Resource
    private MbpReplyMapper mbpReplyMapper;
    /**
     * 发表帖子
     *
     * @param postModel 实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean beforeSave(PostModel postModel) throws Exception {
        postModel.setState(DefaultColumn.STATE.getMessage());
        return mbpPostMapper.insert(postModel) > 0;
    }

    /**
     * 组装帖子里所有相关信息
     *
     * @param id 帖子id
     * @return 帖子详细信息，包括帖子内容，作者，评论，回复
     */
    public Map<String, Object> postDetail(Long id) {
        Map<String, Object> map = new HashMap<>();
        //帖子信息
        QueryWrapper<PostModel> postWrapper = new QueryWrapper<>();
        postWrapper.select("photo_link", "title", "content", "create_date", "id", "author_id")
                .eq("id", id);
        PostModel postModel = mbpPostMapper.selectOne(postWrapper);
        map.put("postInfo", postModel);
        //作者信息
        QueryWrapper<UserModel> userWrapper = new QueryWrapper<>();
        userWrapper.select("id", "nickname", "description", "photo_link")
                .eq("id", postModel.getAuthorId());
        UserModel authorInfo = mbpUserMapper.selectOne(userWrapper);
        map.put("authorInfo", authorInfo);
        //评论信息
        CommentModel commentModel = new CommentModel().setPostId(postModel.getId());
        List<CommentModel> commentAndReplyByPostId = mbpCommentMapper.findCommentAndReplyByModel(commentModel);
        map.put("commentInfo", commentAndReplyByPostId);
        return map;
    }

    /**
     * 实体查询
     *
     * @param model 实体
     */
    public List<PostModel> findByModel(PostModel model) {
        String title = null;
        if (model != null) {
            title = model.getTitle();
            model.setTitle(null);
        }
        QueryWrapper<PostModel> queryWrapper = new QueryWrapper<>(model);
        if (!StrUtil.isBlank(title)) {
            queryWrapper.like("title", title);
        }
        queryWrapper.orderByDesc("create_date");
        return mbpPostMapper.selectList(queryWrapper);
    }

    /**
     * 修改帖子信息
     *
     * @param postModel 实体
     * @return 新的帖子列表
     */
    public List<PostModel> modifyPost(PostModel postModel) {
        UpdateWrapper<PostModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", postModel.getId());
        int update = mbpPostMapper.update(postModel, wrapper);
        if (update > 0) {
            return findByModel(new PostModel());
        }
        throw new MiniBoxException("修改失败");
    }

    /**
     * 删除帖子，要把对应的评论和回复也删了
     *
     * @param id 帖子id
     * @return 成功后返回新的帖子列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<PostModel> removePost(Long id) {
        //帖子
        QueryWrapper<PostModel> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        //评论
        QueryWrapper<CommentModel> commentWrapper = new QueryWrapper<>();
        commentWrapper.eq("post_id", id);
        List<CommentModel> commentModels = mbpCommentMapper.selectList(commentWrapper);
        //如果有评论，才删
        if (commentModels.size() > 0) {
            //回复
            QueryWrapper<ReplyModel> replyWrapper = new QueryWrapper<>();
            replyWrapper.eq("comment_id", commentModels.get(0).getId());
            int deleteReply = mbpReplyMapper.delete(replyWrapper);
            int deleteComment = mbpCommentMapper.delete(commentWrapper);
            if (!(deleteComment > 0) && !(deleteReply > 0)) {
                throw new MiniBoxException("删除失败");
            }
        }
        int deletePost = mbpPostMapper.delete(wrapper);
        if (deletePost > 0) {
            return findByModel(new PostModel());
        }
        throw new MiniBoxException("删除失败");
    }
}
