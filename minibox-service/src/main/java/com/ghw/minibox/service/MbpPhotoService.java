package com.ghw.minibox.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpPhotoMapper;
import com.ghw.minibox.model.PhotoModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 游戏图片 逻辑
 * @date 2021/2/19
 */
@Service
public class MbpPhotoService {
    @Resource
    private MbpPhotoMapper mbpPhotoMapper;

    @Transactional(rollbackFor = Throwable.class)
    public List<PhotoModel> save(PhotoModel photoModel) {
        int insert = mbpPhotoMapper.insert(photoModel);
        if (insert > 0) {
            return mbpPhotoMapper.selectList(null);
        }
        throw new MiniBoxException("添加失败");
    }

    @Transactional(rollbackFor = Throwable.class)
    public List<PhotoModel> remove(Long id, Long gameId) {
        QueryWrapper<PhotoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .eq("game_id", gameId);
        int delete = mbpPhotoMapper.delete(wrapper);
        if (delete > 0) {
            return mbpPhotoMapper.selectList(null);
        }
        throw new MiniBoxException("删除失败");
    }

    public List<PhotoModel> list(PhotoModel photoModel) {
        QueryWrapper<PhotoModel> wrapper = new QueryWrapper<>(photoModel);
        return mbpPhotoMapper.selectList(wrapper);
    }
}
