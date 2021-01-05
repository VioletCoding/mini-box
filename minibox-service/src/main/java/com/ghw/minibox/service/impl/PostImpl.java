package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.entity.*;
import com.ghw.minibox.mapper.*;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Value;
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
public class PostImpl implements CommonService<MbPost> {

    @Resource
    private MbPostMapper postMapper;
    @Resource
    private MbBlockMapper blockMapper;
    @Resource
    private MbUserMapper mbUserMapper;
    @Resource
    private MbCommentMapper commentMapper;
    @Resource
    private MbReplyMapper replyMapper;
    @Resource
    private MapperUtils mapperUtils;
    @Resource
    private QiNiuUtil qiNiuUtil;
    @Value("${qiNiu.link}")
    private String qnLink;

    /**
     * 帖子列表
     *
     * @param param 参数
     * @return 帖子列表
     */
    @AOPLog("帖子列表")
    @Override
    public List<MbPost> selectAll(MbPost param) {
        //得到帖子列表
        List<MbPost> posts = postMapper.queryAll(param);
        //为了组装版块信息
        List<Long> bidList = new ArrayList<>();
        //帖子id集合
        List<Long> tidList = new ArrayList<>();
        //得到所有版块id
        posts.forEach(p -> {
            bidList.add(p.getBid());
            tidList.add(p.getId());
        });
        //获取所有版块信息
        List<MbBlock> mbBlock = new ArrayList<>();
        if (bidList.size() > 0) {
            mbBlock = blockMapper.queryInId(bidList);
        }
        //获取评论数
        List<MbPost> countComment = new ArrayList<>();
        if (tidList.size() > 0) {
            countComment = mapperUtils.countComment(tidList);
        }

        //数据组装
        List<MbBlock> finalMbBlock = mbBlock;
        List<MbPost> finalCountComment = countComment;
        posts.forEach(p -> {
            //版块信息
            finalMbBlock.forEach(p::setMbBlock);
            //因为有些帖子没有评论，所以有些返回值是空的，这样的话评论数就会赋值给错误的帖子，应该判断关联的帖子ID是否相等再赋值，如果为空，赋个0
            finalCountComment.forEach(c -> {
                if (p.getId().equals(c.getId())) {
                    p.setCountComment(c.getCountComment());
                } else {
                    p.setCountComment(0L);
                }
            });
        });

        return posts;
    }

    /**
     * 发布帖子
     *
     * @param entity 实体
     * @return 是否成功
     */
    @AOPLog("发布帖子")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(MbPost entity) {
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
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> upload(MultipartFile[] multipartFiles) throws IOException {

        if (multipartFiles.length < 1) {
            throw new RuntimeException("文件为空");
        }

        List<String> links = new ArrayList<>();

        for (MultipartFile m : multipartFiles) {
            String fastSimpleUUID = IdUtil.fastSimpleUUID();
            //key是文件名，随机生成，使用字节数组上传可以获取上传进度，以及断点续传
            qiNiuUtil.syncUpload(fastSimpleUUID, m.getBytes());
            links.add(this.qnLink + fastSimpleUUID);
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
        //得到 「帖子详情」
        MbPost mbPost = postMapper.queryById(id);
        //获取 「帖子详情」-> 「作者信息」
        MbUser mbUser = mbUserMapper.queryById(mbPost.getUid());
        mbPost.setMbUser(mbUser);

        //获取 「评论」
        List<MbComment> comments = commentMapper.queryAll(new MbComment().setTid(mbPost.getId()));
        //获取 「评论」 -> 「回复」
        List<MbReply> replies = replyMapper.queryAll(new MbReply().setReplyInPost(mbPost.getId()));
        //获取 「评论」 -> 「评论用户的id」
        List<Long> uidList = new ArrayList<>();
        comments.forEach(c -> uidList.add(c.getUid()));
        //获取 「评论」 -> 「用户」
        List<MbUser> users = mbUserMapper.queryInId(uidList);

        //获取评论的人的信息、人的头像、回复列表
        comments.forEach(c -> {
            /*
             * Java 8 lambda 语法塘 等同于
             * users.forEach(u -> { c.setMbUser(u) })
             */
            users.forEach(c::setMbUser);
            c.setReplyList(replies);
        });

        mbPost.setCommentList(comments);

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
