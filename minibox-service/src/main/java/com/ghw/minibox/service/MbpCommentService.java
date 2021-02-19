package com.ghw.minibox.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpCommentMapper;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.mapper.MbpOrderMapper;
import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.OrderModel;
import com.ghw.minibox.utils.DefaultColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Violet
 * @description 评论 业务逻辑层
 * @date 2021/2/2
 */
@Service
public class MbpCommentService {
    @Resource
    private MbpCommentMapper mbpCommentMapper;
    @Resource
    private MbpOrderMapper mbpOrderMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;

    /**
     * 发表评论
     *
     * @param model 实体
     * @return 是否成功
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(CommentModel model) {
        if (model.getPostId() == null && model.getGameId() == null) {
            throw new MiniBoxException("帖子ID或游戏ID为空");
        }
        //如果是游戏下的评论
        if (model.getGameId() != null) {

            QueryWrapper<OrderModel> wrapper = new QueryWrapper<>();
            wrapper.select("id")
                    .eq("game_id", model.getGameId())
                    .eq("user_id", model.getUserId());
            Integer count = mbpOrderMapper.selectCount(wrapper);
            if (count <= 0) {
                throw new MiniBoxException("请先购买游戏");
            }

            if (model.getScore() == null) {
                throw new MiniBoxException("游戏评分不能为空");
            }
            model.setPostId(null);
            //计算游戏的评分
            QueryWrapper<CommentModel> commentWrapper = new QueryWrapper<>();
            commentWrapper.select("score")
                    .eq("game_id", model.getGameId());
            List<CommentModel> commentModels = mbpCommentMapper.selectList(commentWrapper);
            BigDecimal score = new BigDecimal("0.0");
            //计算标准 => 某个游戏的总评分 / 评分人数 = 游戏的平均分
            for (CommentModel commentModel : commentModels) {
                score = score.add(new BigDecimal(commentModel.getScore().toString()));
            }
            System.out.println("score=>" + score);
            if (!score.equals(BigDecimal.ZERO)) {
                String valueOf = String.valueOf(commentModels.size());
                BigDecimal divide = new BigDecimal(valueOf);
                BigDecimal result = score.divide(divide, 1, BigDecimal.ROUND_HALF_UP);
                //评分最低是0分
                if (result.equals(BigDecimal.ZERO)) {
                    result = BigDecimal.ZERO;
                }
                //最高评分是5分
                if (result.compareTo(new BigDecimal("5.0")) >= 0) {
                    result = new BigDecimal("5.0");
                }
                GameModel gameModel = new GameModel().setId(model.getGameId()).setScore(result);
                UpdateWrapper<GameModel> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", model.getGameId());
                mbpGameMapper.update(gameModel, updateWrapper);
            }
        }
        model.setState(DefaultColumn.STATE.getMessage());
        return mbpCommentMapper.insert(model) > 0;
    }

}
