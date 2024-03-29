package com.ghw.minibox.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.*;
import com.ghw.minibox.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 游戏 业务逻辑
 * @date 2021/2/2
 */
@Service
public class MbpGameService {
    @Resource
    private MbpGameMapper mbpGameMapper;
    @Resource
    private MbpPhotoMapper mbpPhotoMapper;
    @Resource
    private MbpTagMapper mbpTagMapper;
    @Resource
    private MbpCommentMapper mbpCommentMapper;
    @Resource
    private NimbusJoseJwt nimbusJoseJwt;
    @Resource
    private MbpOrderMapper mbpOrderMapper;

    /**
     * 检查登录用户是否已经购买了此游戏
     *
     * @param request request
     * @param gameId  游戏id
     * @return true 已经购买 ，false 未购买
     */
    private boolean buyFlag(HttpServletRequest request, Long gameId) throws Exception {
        //1.解析token
        String accessToken = request.getHeader("accessToken");
        PayloadDto payloadDto = nimbusJoseJwt.verifyTokenByHMAC(accessToken);
        //2.查看当前登录用户是否购买此游戏
        QueryWrapper<OrderModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("game_id", gameId)
                .eq("user_id", payloadDto.getUserId());
        OrderModel orderModel = mbpOrderMapper.selectOne(queryWrapper);
        return orderModel != null;
    }

    /**
     * 每个游戏只能评论一次，防止刷评分
     *
     * @param request request
     * @param gameId  游戏id
     * @return true 已经评论过了  false 未评论
     */
    public boolean commentFlag(HttpServletRequest request, Long gameId) throws Exception {
        //1.解析token
        String accessToken = request.getHeader("accessToken");
        PayloadDto payloadDto = nimbusJoseJwt.verifyTokenByHMAC(accessToken);
        //2.只能评论一次
        QueryWrapper<CommentModel> countWrapper = new QueryWrapper<>();
        countWrapper.eq("game_id", gameId)
                .eq("user_id", payloadDto.getUserId());
        Integer once = mbpCommentMapper.selectCount(countWrapper);
        return once > 0;
    }

    /**
     * 游戏详情里的内容，包括游戏信息、评分信息、评论信息
     *
     * @param id 游戏id
     */
    public Map<String, Object> gameDetail(HttpServletRequest request, Long id) throws Exception {
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
        //购买标识
        boolean buyFlag = this.buyFlag(request, id);
        map.put("buyFlag", buyFlag);
        boolean commentFlag = this.commentFlag(request, gameModel.getId());
        map.put("commentFlag", commentFlag);
        return map;
    }


    /**
     * 游戏 条件查询
     *
     * @param model 实体
     * @return 游戏列表
     */
    public List<GameModel> findByModel(GameModel model) {
        String name = null;
        if (model != null) {
            name = model.getName();
            model.setName(null);
        }
        QueryWrapper<GameModel> wrapper = new QueryWrapper<>(model);
        if (!StrUtil.isBlank(name)) {
            wrapper.like("name", name);
        }
        return mbpGameMapper.selectList(wrapper);
    }

    /**
     * 添加游戏
     *
     * @param gameModel 实体
     * @return 返回游戏列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<GameModel> addGame(GameModel gameModel) {
        int insert = mbpGameMapper.insert(gameModel);
        if (insert > 0) {
            return findByModel(null);
        }
        throw new MiniBoxException("添加失败");
    }

    /**
     * 修改游戏
     *
     * @param gameModel 实体
     * @return 新的游戏列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<GameModel> modifyGame(GameModel gameModel) {
        if (gameModel.getId() == null) {
            throw new MiniBoxException("游戏ID为空");
        }
        UpdateWrapper<GameModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", gameModel.getId());
        int update = mbpGameMapper.update(gameModel, wrapper);
        if (update > 0) {
            return findByModel(null);
        }
        throw new MiniBoxException("更新失败");
    }

    /**
     * 删除游戏
     *
     * @param id 游戏id
     * @return 新的游戏列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<GameModel> removeGame(Long id) {
        int i = mbpGameMapper.deleteById(id);
        if (i > 0) {
            return findByModel(null);
        }
        throw new MiniBoxException("删除失败");
    }
}
