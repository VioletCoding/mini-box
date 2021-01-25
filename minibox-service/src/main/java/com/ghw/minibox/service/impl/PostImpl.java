package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.exception.MyException;
import com.ghw.minibox.mapper.MapperUtils;
import com.ghw.minibox.mapper.MbCommentMapper;
import com.ghw.minibox.mapper.MbPostMapper;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.QiNiuUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 帖子实现
 * @date 2021/1/4
 */
@Service
@Slf4j
public class PostImpl implements CommonService<MbPost> {

    @Resource
    private MbPostMapper postMapper;
    @Resource
    private MbCommentMapper commentMapper;
    @Resource
    private MbUserMapper userMapper;
    @Resource
    private MapperUtils mapperUtils;
    @Resource
    private QiNiuUtil qiNiuUtil;

    /**
     * 帖子列表
     *
     * @param param 参数
     * @return 帖子列表
     */
    @AOPLog("帖子列表")
    @Override
    public List<MbPost> selectAll(MbPost param) {
        //TODO 对于多表查询，那么最多LEFT JOIN 2个表，分批组装数据，这里需要改进，评论列表等也需要改进
        return mapperUtils.queryPost(null);
    }


    @AOPLog("用户个人信息显示自己的评论，以及评论在哪个帖子下发布的")
    public Object getCommentAndPostByUid(Long uid) {
        return mapperUtils.queryUserAllCommentInPost(uid);
    }

    /**
     * 发布帖子
     *
     * @param entity 实体
     * @return 是否成功
     */
    @AOPLog("发布帖子")
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Object insert(MbPost entity) {
        int insert = postMapper.insert(entity);
        return insert > 0;
    }

    /**
     * 七牛云文件上传，可以批量上传
     *
     * @param multipartFiles 多部件接口
     * @return map集合，里面有图片的全部链接
     * @throws IOException -
     */
    @AOPLog("文件上传")
    @Transactional(rollbackFor = Throwable.class)
    public Map<String, Object> upload(MultipartFile[] multipartFiles) throws IOException {

        if (multipartFiles.length < 1)
            throw new MyException("文件为空");

        List<String> links = new ArrayList<>();

        for (MultipartFile m : multipartFiles) {
            String fastSimpleUUID = IdUtil.fastSimpleUUID();
            //key是文件名，随机生成，使用字节数组上传可以获取上传进度，以及断点续传
            String link = qiNiuUtil.syncUpload(fastSimpleUUID, m.getBytes());
            links.add(link);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("images", links);
        return map;
    }

    /**
     * 展示帖子详情
     *
     * @param id 帖子id
     * @return 帖子详情
     */
    @AOPLog("帖子详情")
    @Override
    public MbPost selectOne(Long id) {
        //得到帖子信息
        MbPost mbPost = postMapper.queryById(id);
        //获得作者信息
        MbUser mbUser = userMapper.queryById(mbPost.getUid());
        mbPost.setMbUser(mbUser);
        //通过帖子ID得到对应的评论列表和回复列表
        List<MbComment> mbComments = commentMapper.queryAllWithReply(new MbComment().setTid(mbPost.getId()));
        mbPost.setCommentList(mbComments);
        return mbPost;
    }

    @Override
    public boolean update(MbPost entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
