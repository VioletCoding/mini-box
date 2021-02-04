package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.mapper.MbpCommentMapper;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.mapper.MbpPhotoMapper;
import com.ghw.minibox.mapper.MbpTagMapper;
import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.PhotoModel;
import com.ghw.minibox.model.TagModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 游戏 业务逻辑
 * @date 2021/2/2
 */
@Service
public class MbpGameServiceImpl {
    @Resource
    private MbpGameMapper mbpGameMapper;
    @Resource
    private MbpPhotoMapper mbpPhotoMapper;
    @Resource
    private MbpTagMapper mbpTagMapper;
    @Resource
    private MbpCommentMapper mbpCommentMapper;

    /**
     * 游戏详情里的内容，包括游戏信息、评分信息、评论信息
     *
     * @param id 游戏id
     */
    public Map<String, Object> gameDetail(Long id) {
        HashMap<String, Object> map = new HashMap<>();
        //游戏详情
        QueryWrapper<GameModel> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        GameModel gameModel = mbpGameMapper.selectOne(wrapper);
        map.put("gameInfo", gameModel);
        //游戏图片
        QueryWrapper<PhotoModel> photoWrapper = new QueryWrapper<>();
        photoWrapper.select("photo_link")
                .eq("game_id", gameModel.getId());
        List<PhotoModel> photoModelList = mbpPhotoMapper.selectList(photoWrapper);
        map.put("gamePhotos", photoModelList);
        //游戏标签
        QueryWrapper<TagModel> tagWrapper = new QueryWrapper<>();
        tagWrapper.select("tag_name").eq("game_id", gameModel.getId());
        List<TagModel> tagModels = mbpTagMapper.selectList(tagWrapper);
        map.put("tags", tagModels);
        //游戏评论
        CommentModel commentModel = new CommentModel().setGameId(gameModel.getId());
        List<CommentModel> commentInfo = mbpCommentMapper.findCommentAndReplyByModel(commentModel);
        map.put("commentInfo", commentInfo);
        return map;
    }


    /**
     * 游戏 条件查询
     *
     * @param model 实体
     * @return 游戏列表
     */
    public List<GameModel> findByModel(GameModel model) {
        QueryWrapper<GameModel> wrapper = new QueryWrapper<>(model);
        wrapper.select("id", "name", "price", "description", "origin_price", "photo_link");
        return mbpGameMapper.selectList(wrapper);
    }
}
