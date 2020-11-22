package com.ghw.minibox.utils;

import lombok.Getter;

/**
 * @author Violet
 * @description 数据库的该条记录是否可用，0可用，1不可用
 * @date 2020/11/22
 */
@Getter
public enum RecordStatus {

    ENABLE(0),
    DISABLE(1);


    private final int status;

    RecordStatus(int status) {
        this.status = status;
    }
}
