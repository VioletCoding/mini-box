package com.ghw.minibox.utils;

import lombok.Getter;

/**
 * @author Violet
 * @description 评论类型
 * @date 2020/11/22
 */
@Getter
public enum PostType {
    /**
     * 在帖子下的普通评论
     */
    COMMENT_IN_POST("TC"),
    /**
     * 在游戏详情下的评论
     */
    COMMENT_IN_GAME("GC"),
    /**
     * 帖子下的回复
     */
    REPLY_IN_POST("TR"),
    /**
     * 游戏下的回复
     */
    REPLY_IN_GAME("GR");

    private final String type;

    PostType(String type) {
        this.type = type;
    }
}
