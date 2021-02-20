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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private MbpGameService mbpGameService;

    /**
     * 发表评论
     *
     * @param model 实体
     * @return 是否成功
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(HttpServletRequest request, CommentModel model) throws Exception {
        if (model.getPostId() == null && model.getGameId() == null) {
            throw new MiniBoxException("帖子ID或游戏ID为空");
        }
        //1.如果是游戏下的评论
        if (model.getGameId() != null) {
            QueryWrapper<OrderModel> wrapper = new QueryWrapper<>();
            wrapper.select("id")
                    .eq("game_id", model.getGameId())
                    .eq("user_id", model.getUserId());
            Integer count = mbpOrderMapper.selectCount(wrapper);
            if (count == 0) {
                throw new MiniBoxException("请先购买游戏");
            }
            if (model.getScore() == null) {
                throw new MiniBoxException("游戏评分不能为空");
            }
            //2.只能评论一次
            boolean flag = mbpGameService.commentFlag(request, model.getGameId());
            if (flag) {
                throw new MiniBoxException("只能评论一次");
            }
            model.setPostId(null);
            updateGameScore(model.getGameId());
        }
        model.setState(DefaultColumn.STATE.getMessage());
        return mbpCommentMapper.insert(model) > 0;
    }

    /**
     * 更新游戏的评分，异步操作
     *
     * @param gameId 游戏id
     */
    @Async
    protected void updateGameScore(Long gameId) {
        //2.计算游戏的评分
        QueryWrapper<CommentModel> commentWrapper = new QueryWrapper<>();
        commentWrapper.select("score").eq("game_id", gameId);
        List<CommentModel> commentModels = mbpCommentMapper.selectList(commentWrapper);
        BigDecimal score = new BigDecimal("0.0");
        //3.计算标准 => 某个游戏的总评分 / 评分人数 = 游戏的平均分
        for (CommentModel commentModel : commentModels) {
            score = score.add(new BigDecimal(commentModel.getScore().toString()));
        }
        //4.注意除数不能为0，否则算术异常
        if (score.compareTo(BigDecimal.ZERO) > 0) {
            String valueOf = String.valueOf(commentModels.size());
            BigDecimal divide = new BigDecimal(valueOf);
            //5.注意这里，BigDecimal的除法，假设除不尽，必须指定 保留的小数位数、舍入模式。
            //6.score=总评分数 除以 divide=被除数（评论人数） 等于 游戏总分
            //7.这里舍入模式为四舍五入，保留一位小数
            BigDecimal result = score.divide(divide, 1, BigDecimal.ROUND_HALF_UP);
            //8.评分最低是0分
            if (result.compareTo(BigDecimal.ZERO) <= 0) {
                result = BigDecimal.ZERO;
            }
            //9.最高评分是5分
            BigDecimal max = new BigDecimal("5.0");
            if (result.compareTo(max) >= 0) {
                result = max;
            }
            //10.更新该游戏的评分
            updateScore(gameId, result);
        }
    }

    /**
     * 写入数据库，为了和@Async分离开，@Transactional和@Async一起使用会失效
     *
     * @param gameId 游戏id
     * @param score  分数
     */
    @Transactional(rollbackFor = Throwable.class)
    protected void updateScore(Long gameId, BigDecimal score) {
        GameModel gameModel = new GameModel().setId(gameId).setScore(score);
        UpdateWrapper<GameModel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", gameId);
        mbpGameMapper.update(gameModel, updateWrapper);
    }

}
