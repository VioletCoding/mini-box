package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.mapper.MbpCommentMapper;
import com.ghw.minibox.mapper.MbpPostMapper;
import com.ghw.minibox.mapper.MbpUserMapper;
import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.PostModel;
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
public class MbpPostServiceImpl{
    @Resource
    private MbpPostMapper mbpPostMapper;
    @Resource
    private MbpUserMapper mbpUserMapper;
    @Resource
    private MbpCommentMapper mbpCommentMapper;
    @Resource
    private NimbusJoseJwt nimbusJoseJwt;

    /**
     * 发表帖子之前
     * @param postModel 实体
     * @param token token
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean beforeSave(PostModel postModel, String token) throws Exception {
        PayloadDto payloadDto = nimbusJoseJwt.verifyTokenByHMAC(token);
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", payloadDto.getUsername());
        UserModel userModel = mbpUserMapper.selectOne(queryWrapper);
        if (userModel == null) {
            return false;
        }
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
     * @param model 实体
     */
    public List<PostModel> findByModel(PostModel model) {
        QueryWrapper<PostModel> queryWrapper = new QueryWrapper<>(model);
        queryWrapper.orderByDesc("create_date");
        return mbpPostMapper.selectList(queryWrapper);
    }
}
