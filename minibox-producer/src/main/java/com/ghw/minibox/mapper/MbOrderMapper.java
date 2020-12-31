package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbOrder;

import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2020/12/31
 */

public interface MbOrderMapper {

    MbOrder queryById(Long id);

    List<MbOrder> queryAll(MbOrder mbOrder);

    int insert(MbOrder mbOrder);

    int update(MbOrder mbOrder);

    int delete(MbOrder mbOrder);

}
