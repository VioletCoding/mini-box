package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.service.MbPhotoService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * (MbPhoto)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:14
 */
@Service
public class MbPhotoServiceImpl implements MbPhotoService {
    @Resource
    private MbPhotoMapper mbPhotoMapper;
    @Resource
    private QiNiuUtil qn;
    @Value("${qiNiu.link}")
    private String link;


    /**
     * 通过ID查询单条数据
     *
     * @param pid 主键
     * @return 实例对象
     */
    @Override
    public MbPhoto queryById(Long pid) {
        return this.mbPhotoMapper.queryById(pid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbPhoto> queryAllByLimit(int offset, int limit) {
        return this.mbPhotoMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbPhoto 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MbPhoto insert(MbPhoto mbPhoto) {
        this.mbPhotoMapper.insert(mbPhoto);
        return mbPhoto;
    }

    /**
     * 修改数据
     *
     * @param file 文件对象
     * @return 实例对象
     */
    @AOPLog("修改头像")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MbPhoto update(MultipartFile file, Long uid) throws IOException {
        MbPhoto mbPhoto = new MbPhoto().setUid(uid);
        //生成文件key
        String simpleUUID = IdUtil.fastSimpleUUID();
        //同步上传
        qn.syncUpload(simpleUUID, file.getBytes());
        //删除七牛云对应的文件
        MbPhoto photo = queryById(uid);
        qn.delete(photo.getPhotoLink().split(this.link)[1]);

        mbPhoto.setPhotoLink(this.link + simpleUUID);
        //更新数据库
        mbPhotoMapper.update(mbPhoto);
        return queryById(uid);
    }

    /**
     * 通过主键删除数据
     *
     * @param pid 主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long pid) {
        return this.mbPhotoMapper.deleteById(pid) > 0;
    }
}