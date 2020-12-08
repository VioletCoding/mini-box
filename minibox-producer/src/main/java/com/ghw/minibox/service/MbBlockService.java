package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbBlock;

import java.util.List;

/**
 * (MbBlock)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:09
 */
public interface MbBlockService {
    /**
     * 首页展示所有版块
     *
     * @return 所有版块的名称，仅仅只是名称和bid（版块ID）
     */
    List<MbBlock> showAllBlock();

}