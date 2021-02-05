package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpBlockMapper;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.model.BlockModel;
import com.ghw.minibox.model.GameModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 版块 业务逻辑 层
 * @date 2021/2/1
 */
@Service
public class MbpBlockServiceImpl {
    @Resource
    private MbpBlockMapper mbpBlockMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;

    /**
     * 条件或全查版块
     *
     * @param model 实体
     * @return 版块列表
     */
    public List<BlockModel> findByModel(BlockModel model) {
        List<BlockModel> blockModelList;
        if (model == null) {
            blockModelList = mbpBlockMapper.selectList(null);
        } else {
            QueryWrapper<BlockModel> queryWrapper = new QueryWrapper<>(model);
            blockModelList = mbpBlockMapper.selectList(queryWrapper);
        }
        return blockModelList;
    }

    /**
     * 添加版块
     *
     * @param blockModel 实体
     * @return 新的版块列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<BlockModel> addBlock(BlockModel blockModel) {
        int insert = mbpBlockMapper.insert(blockModel);
        if (insert > 0) {
            return findByModel(null);
        } else {
            throw new MiniBoxException("添加失败");
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public List<BlockModel> modify(BlockModel blockModel) {
        UpdateWrapper<BlockModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", blockModel.getId());
        int update = mbpBlockMapper.update(blockModel, wrapper);
        if (update > 0) {
            return findByModel(null);
        } else {
            throw new MiniBoxException("修改失败");
        }
    }

    /**
     * 删除版块，会检查关联的游戏是否还有，如果还有就不给删
     *
     * @param id 版块id
     * @return 新的版块列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<BlockModel> delete(Long id) {
        QueryWrapper<BlockModel> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        BlockModel blockModel = mbpBlockMapper.selectOne(wrapper);
        if (blockModel != null) {
            Long gameId = blockModel.getGameId();
            QueryWrapper<GameModel> gameModelQueryWrapper = new QueryWrapper<>();
            gameModelQueryWrapper.eq("id", gameId);
            GameModel gameModel = mbpGameMapper.selectOne(gameModelQueryWrapper);
            if (gameModel != null) {
                throw new MiniBoxException("该版块下还有游戏，请先删除与之关联的所有游戏");
            }
            int delete = mbpBlockMapper.deleteById(id);
            if (delete > 0) {
                return findByModel(null);
            }
            throw new MiniBoxException("删除失败");
        }
        throw new MiniBoxException("未找到相关版块");
    }
}
