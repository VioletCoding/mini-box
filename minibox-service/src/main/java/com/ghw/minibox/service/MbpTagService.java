package com.ghw.minibox.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.mapper.MbpTagMapper;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.TagModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 标签管理
 * @date 2021/2/19
 */
@Service
public class MbpTagService {
    @Resource
    private MbpTagMapper mbpTagMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;

    @Transactional(rollbackFor = Throwable.class)
    public List<TagModel> save(TagModel tagModel) {
        if (tagModel.getGameId() != null) {
            GameModel gameModel = mbpGameMapper.selectById(tagModel.getGameId());
            if (gameModel != null) {
                int insert = mbpTagMapper.insert(tagModel);
                if (insert > 0) {
                    return mbpTagMapper.selectList(null);
                }
            }
        }
        throw new MiniBoxException("添加失败");
    }


    @Transactional(rollbackFor = Throwable.class)
    public List<TagModel> remove(Long id, Long gameId) {
        QueryWrapper<TagModel> wrapper = new QueryWrapper<>();
        wrapper.eq("game_id", gameId)
                .eq("id", id);
        int delete = mbpTagMapper.delete(wrapper);
        if (delete > 0) {
            return mbpTagMapper.selectList(null);
        }
        throw new MiniBoxException("删除失败");
    }

    public List<TagModel> list(TagModel tagModel) {
        QueryWrapper<TagModel> wrapper = new QueryWrapper<>(tagModel);
        return mbpTagMapper.selectList(wrapper);
    }


}
