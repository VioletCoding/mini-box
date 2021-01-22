package com.ghw.minibox.utils;

import cn.hutool.core.util.IdUtil;
import com.qiniu.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Violet
 * @description 文件上传到本地，七牛云老过期，懒得用了
 * @date 2021/1/22
 */
@Component
public class LocalFileManager {
    private final String localPath = "http://192.168.0.105:20001/"+"../../FileUpload/";

    /**
     * 上传文件
     *
     * @param multipartFile 文件对象
     * @return 文件完整路径
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty())
            throw new MyException("multipartFile为空");

        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = null;
        if (!MbStringUtils.isNullOrEmpty(originalFilename))
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = IdUtil.fastSimpleUUID() + suffix;

        String path = this.localPath + fileName;
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            if (!mkdir)
                throw new MyException("创建目录失败");
        }
        multipartFile.transferTo(file);
        return path;
    }

    /**
     * 删除文件
     *
     * @param fullPath 完整路径 + 文件名
     * @return 是否成功
     */
    public boolean delete(String fullPath) {
        if (StringUtils.isNullOrEmpty(fullPath))
            throw new MyException("文件key为空或是空字符串");
        File file = new File(fullPath);
        if (!file.exists())
            throw new MyException("文件未找到");
        return file.delete();
    }

}
