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
     * 回复他人的评论
     */
    COMMENT_IN_REPLY("RC"),
    /**
     * 在游戏详情下的评论
     */
    COMMENT_IN_GAME("GC"),
    /**
     * 用户头像
     */
    PHOTO_USER("UP"),
    /**
     * 帖子带图
     */
    PHOTO_POST("TP"),
    /**
     * 评论带图
     */
    PHOTO_COMMENT("CP"),
    /**
     * 游戏图片
     */
    PHOTO_GAME("GP");

    private final String type;

    PostType(String type) {
        this.type = type;
    }
}
