package com.ghw.minibox.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 工具类mapper
 * @date 2021/2/5
 */

public interface MbpUtilMapper {

    List<Map<String, Object>> commentPerDay();

    List<Map<String, Object>> postPerDay();

    List<Map<String,Object>> gameSalesRankings();

}
