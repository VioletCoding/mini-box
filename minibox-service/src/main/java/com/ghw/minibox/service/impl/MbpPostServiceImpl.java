package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.mapper.MbpPostMapper;
import com.ghw.minibox.mapper.MbpUserMapper;
import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.model.UserModel;
import com.ghw.minibox.service.BaseService;
import com.ghw.minibox.utils.DefaultColumn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 帖子 业务逻辑实现
 * @date 2021/2/1
 */
@Service
public class MbpPostServiceImpl implements BaseService<PostModel> {
    @Resource
    private MbpPostMapper mbpPostMapper;
    @Resource
    private MbpUserMapper mbpUserMapper;
    @Resource
    private NimbusJoseJwt nimbusJoseJwt;

    public boolean beforeSave(PostModel postModel, String token) throws Exception {
        PayloadDto payloadDto = nimbusJoseJwt.verifyTokenByHMAC(token);
        if (payloadDto == null) {
            return false;
        }
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", payloadDto.getUsername());
        UserModel userModel = mbpUserMapper.selectOne(queryWrapper);
        if (userModel == null) {
            return false;
        }
        postModel.setAuthorNickname(userModel.getNickname());
        postModel.setAuthorPhotoLink(userModel.getPhotoLink());
        postModel.setState(DefaultColumn.STATE.getMessage());
        return save(postModel);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(PostModel model) {
        return mbpPostMapper.insert(model) > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean modify(PostModel model) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public PostModel findOneById(Long id) {
        return null;
    }

    @Override
    public PostModel findOne(String column, Object value) {
        return null;
    }

    @Override
    public List<PostModel> findByModel(PostModel model) {
        QueryWrapper<PostModel> queryWrapper = new QueryWrapper<>(model);
        queryWrapper.orderByDesc("create_date");
        return mbpPostMapper.selectList(queryWrapper);
    }
}
