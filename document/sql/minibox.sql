/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : minibox

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 22/12/2020 15:42:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mb_block
-- ----------------------------
DROP TABLE IF EXISTS `mb_block`;
CREATE TABLE `mb_block`
(
    `bid`         bigint(11)                                                   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '版块名称，一般是【游戏名称】，如果不是【游戏名称】，就是【杂谈】',
    `gid`         bigint(11)                                                   NOT NULL COMMENT '关联的游戏id',
    `state`       int(1)                                                       NOT NULL DEFAULT 0 COMMENT '记录状态，0有效，1无效',
    `create_time` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`bid`) USING BTREE,
    INDEX `block_bid_index` (`bid`) USING BTREE COMMENT '主键索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_comment
-- ----------------------------
DROP TABLE IF EXISTS `mb_comment`;
CREATE TABLE `mb_comment`
(
    `cid`         bigint(11)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `state`       int(1)                                                         NOT NULL DEFAULT 0 COMMENT '记录状态，0有效，1无效',
    `content`     varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
    `type`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '评论类型，TC为正常帖子下的评论，RC是回复其他用户的评论，GC是游戏下的评论',
    `tid`         bigint(11)                                                     NULL     DEFAULT NULL COMMENT '帖子ID，如果type=TC，该字段必填',
    `uid`         bigint(11)                                                     NULL     DEFAULT NULL COMMENT '用户ID，如果type=RC，该字段必填',
    `gid`         bigint(11)                                                     NULL     DEFAULT NULL COMMENT '游戏ID，如果type=GC，该字段必填',
    `score`       decimal(18, 1)                                                 NULL     DEFAULT NULL COMMENT '用户对游戏的评分，如果type=GC，该字段必填',
    `create_date` datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date` datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`cid`) USING BTREE,
    INDEX `mb_comment_cid_index` (`cid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_game
-- ----------------------------
DROP TABLE IF EXISTS `mb_game`;
CREATE TABLE `mb_game`
(
    `gid`          bigint(11)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键，自增，默认从10000开始自增，建表时候指定，唯一标识 ',
    `name`         varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '游戏中文名',
    `price`        decimal(18, 2)                                                 NOT NULL DEFAULT 0.00 COMMENT '游戏价格，保留两位小数，注意默认是0.00，否则会在加减乘除上带来转换问题',
    `state`        int(1)                                                         NOT NULL DEFAULT 0 COMMENT '记录状态，0有效，1无效',
    `game_state`   int(1)                                                         NOT NULL DEFAULT 0 COMMENT '游戏状态,0有效，1无效，如游戏下架等。',
    `score`        decimal(11, 1)                                                 NOT NULL DEFAULT 0.0 COMMENT '游戏评分，最大5分，保留一位小数，算法为所有评分总和除以评分人数=评分',
    `description`  varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏简介',
    `release_time` timestamp(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
    `developer`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '开发商',
    `publisher`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '发行商',
    `score_count`  int(11)                                                        NOT NULL DEFAULT 0 COMMENT '评分人数',
    `create_date`  datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date`  datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `origin_price` decimal(18, 2)                                                 NOT NULL DEFAULT 0.00 COMMENT '游戏原价',
    `cover_img`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '游戏封面大图',
    PRIMARY KEY (`gid`) USING BTREE,
    INDEX `mb_game_gid_index` (`gid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_photo
-- ----------------------------
DROP TABLE IF EXISTS `mb_photo`;
CREATE TABLE `mb_photo`
(
    `pid`         bigint(11)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '图片类型，枚举，用户头像UP、帖子内图片TP、帖子头图HP、评论图片CP、游戏图片GP',
    `link`        varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片链接',
    `uid`         bigint(11)                                                    NULL     DEFAULT NULL COMMENT '如果type=UP，该字段必填',
    `tid`         bigint(11)                                                    NULL     DEFAULT NULL COMMENT '如果type=TP，该字段必填',
    `cid`         bigint(11)                                                    NULL     DEFAULT NULL COMMENT '如果type=CP，该字段必填',
    `gid`         bigint(11)                                                    NULL     DEFAULT NULL COMMENT '如果type=GP，该字段必填',
    `state`       int(1)                                                        NOT NULL DEFAULT 0 COMMENT '状态，记录该条状态是否有效,0有效，1无效',
    `create_date` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`pid`) USING BTREE,
    INDEX `post_photo_tid_index` (`tid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_post
-- ----------------------------
DROP TABLE IF EXISTS `mb_post`;
CREATE TABLE `mb_post`
(
    `tid`         bigint(11)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid`         bigint(11)                                                     NOT NULL COMMENT '关联的用户ID',
    `bid`         bigint(11)                                                     NOT NULL DEFAULT 10000 COMMENT '记录这个帖子在哪个版块下发表的，默认是【杂谈】',
    `title`       varchar(41) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '帖子标题',
    `content`     varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帖子内容',
    `post_state`  int(1)                                                         NOT NULL DEFAULT 0 COMMENT '帖子状态，0有效，1已删除',
    `create_date` datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date` datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `state`       int(1)                                                         NOT NULL DEFAULT 0 COMMENT '状态，记录该条状态是否有效,0有效，1无效',
    `cover_img`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '帖子的封面图，记录的是链接地址',
    PRIMARY KEY (`tid`) USING BTREE,
    INDEX `post_tid_index` (`tid`) USING BTREE COMMENT '主键索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_reply
-- ----------------------------
DROP TABLE IF EXISTS `mb_reply`;
CREATE TABLE `mb_reply`
(
    `rid`              bigint(11)                                                     NOT NULL AUTO_INCREMENT COMMENT '回复ID',
    `type`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '回复类型，在哪里回复，TR表示在文章（帖子）下回复他们的评论,GR表示在游戏下回复的评论',
    `reply_who`        bigint(11)                                                     NOT NULL COMMENT '回复谁，填写UID',
    `reply_content`    varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '回复内容',
    `reply_date`       datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '回复时间',
    `reply_uid`        bigint(11)                                                     NOT NULL COMMENT '谁发表的回复',
    `reply_in_post`    bigint(11)                                                     NULL     DEFAULT NULL COMMENT '在哪篇文章（帖子）下的回复',
    `reply_in_comment` bigint(11)                                                     NOT NULL COMMENT '回复了哪一条评论',
    `reply_in_game`    bigint(11)                                                     NULL     DEFAULT NULL COMMENT '在哪个游戏下的评论的回复',
    `status`           int(1)                                                         NOT NULL DEFAULT 0 COMMENT '记录是否有效，默认0有效，1无效',
    PRIMARY KEY (`rid`) USING BTREE,
    INDEX `mb_reply_rid_index` (`rid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_role
-- ----------------------------
DROP TABLE IF EXISTS `mb_role`;
CREATE TABLE `mb_role`
(
    `rid`         bigint(11)                                                   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'USER' COMMENT '角色名称，枚举值USER | ADMIN',
    `state`       int(1)                                                       NOT NULL DEFAULT 0 COMMENT '状态，记录当前记录是否有效，0有效，1无效',
    `create_date` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic
  AUTO_INCREMENT = 10000;

-- ----------------------------
-- Table structure for mb_tag
-- ----------------------------
DROP TABLE IF EXISTS `mb_tag`;
CREATE TABLE `mb_tag`
(
    `tid`         bigint(11)                                                   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tag_name`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
    `gid`         bigint(11)                                                   NOT NULL COMMENT '关联的游戏ID',
    `state`       int(1)                                                       NOT NULL DEFAULT 0 COMMENT '状态，记录当前记录是否有效，0有效，1无效',
    `create_date` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date` datetime(0)                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_user
-- ----------------------------
DROP TABLE IF EXISTS `mb_user`;
CREATE TABLE `mb_user`
(
    `uid`            bigint(11)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nickname`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '昵称',
    `username`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名，本系统是邮箱，需要程序校验',
    `password`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码，MD5加密',
    `description`    varchar(41) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '这个人很懒，什么也没写' COMMENT '个人简介',
    `level`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'LV1' COMMENT '个人等级',
    `wallet_balance` decimal(18, 2)                                                NOT NULL DEFAULT 0.00 COMMENT '钱包余额',
    `user_state`     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'NORMAL' COMMENT '用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）',
    `create_date`    datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户创建日期',
    `exp`            int(2)                                                        NOT NULL DEFAULT 0 COMMENT '经验，每次升级需要10经验，升级后该字段值update为0',
    `update_date`    datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '字段更新时间，修改该条记录则自动更新这个字段',
    `state`          int(1)                                                        NOT NULL DEFAULT 0 COMMENT '状态，记录当前记录是否有效，0有效，1无效',
    PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mb_user_game
-- ----------------------------
DROP TABLE IF EXISTS `mb_user_game`;
CREATE TABLE `mb_user_game`
(
    `id`          bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `gid`         bigint(11)  NOT NULL COMMENT '游戏ID',
    `uid`         bigint(11)  NOT NULL COMMENT '用户ID',
    `state`       int(1)      NOT NULL DEFAULT 0 COMMENT '状态，记录当前记录是否有效，0有效，1无效',
    `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic
  AUTO_INCREMENT = 10000;

-- ----------------------------
-- Table structure for mb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mb_user_role`;
CREATE TABLE `mb_user_role`
(
    `id`          bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid`         bigint(11)  NOT NULL COMMENT '关联的用户ID',
    `rid`         bigint(11)  NOT NULL COMMENT '关联的角色ID',
    `state`       int(1)      NOT NULL DEFAULT 0 COMMENT '状态，记录当前记录是否有效，0有效，1无效',
    `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic
  AUTO_INCREMENT = 10000;

SET FOREIGN_KEY_CHECKS = 1;