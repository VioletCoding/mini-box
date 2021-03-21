/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL-3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : minibox

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 20/02/2021 19:44:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mb_block
-- ----------------------------
DROP TABLE IF EXISTS `mb_block`;
CREATE TABLE `mb_block`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '版块名称，一般是【游戏名称】，如果不是【游戏名称】，就是【杂谈】',
  `photo_link` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '版块图片',
  `game_id` bigint(11) UNSIGNED NOT NULL COMMENT '关联的游戏id',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '记录状态，1有效，0无效',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `block_bid_index`(`id`) USING BTREE COMMENT '主键索引'
) ENGINE = InnoDB AUTO_INCREMENT = 10024 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_block
-- ----------------------------
INSERT INTO `mb_block` VALUES (10009, '怪物猎人：世界', 'http://qorvzwz4v.hn-bkt.clouddn.com/68e7eff9395d4ca5ad9e0c0c5055217a201706131605508504.jpg', 10010, '1', '2021-02-19 22:47:03', '2021-02-19 22:47:03');
INSERT INTO `mb_block` VALUES (10010, '彩虹六号：围攻', 'http://qorvzwz4v.hn-bkt.clouddn.com/6c6d2ee58d81455e99df4e7a040dd715359550_library_hero.jpg', 10011, '1', '2021-02-19 22:48:04', '2021-02-19 22:48:04');
INSERT INTO `mb_block` VALUES (10011, '巫师 3：狂猎', 'http://qorvzwz4v.hn-bkt.clouddn.com/2b6398283450444d8b37af108556feaa292030_library_hero.jpg', 10012, '1', '2021-02-19 22:48:47', '2021-02-19 22:48:47');
INSERT INTO `mb_block` VALUES (10012, '星露谷物语', 'http://qorvzwz4v.hn-bkt.clouddn.com/9eac61f173ba4f9ab4d552c7c80ae675ss_b887651a93b0525739049eb4194f633de2df75be.jpg', 10013, '1', '2021-02-19 22:49:23', '2021-02-19 22:49:23');
INSERT INTO `mb_block` VALUES (10013, '赛博朋克2077', 'http://qorvzwz4v.hn-bkt.clouddn.com/fd09def79a8c46fd8b96905a66fcaf5b1091500_library_hero.jpg', 10014, '1', '2021-02-19 22:50:35', '2021-02-19 22:50:35');
INSERT INTO `mb_block` VALUES (10014, '戴森球计划', 'http://qorvzwz4v.hn-bkt.clouddn.com/3eb2ee2862fa4f3f8daa6b927fe71ab91366540_library_hero.jpg', 10015, '1', '2021-02-19 22:51:27', '2021-02-19 22:51:27');
INSERT INTO `mb_block` VALUES (10015, '欧洲卡车模拟2', 'http://qorvzwz4v.hn-bkt.clouddn.com/0f3e982bbdd242a383e11dd754158b8b227300_library_hero.jpg', 10016, '1', '2021-02-19 22:52:13', '2021-02-19 22:52:13');
INSERT INTO `mb_block` VALUES (10016, '太吾绘卷', 'http://qorvzwz4v.hn-bkt.clouddn.com/1bcbd77b9ef944f2b16f6f7a85248109838350_library_hero.jpg', 10017, '1', '2021-02-19 22:52:52', '2021-02-19 22:52:52');
INSERT INTO `mb_block` VALUES (10017, '杀戮尖塔', 'http://qorvzwz4v.hn-bkt.clouddn.com/dca06d89c1b849f5a9366c3edee021d8646570_library_hero.jpg', 10018, '1', '2021-02-19 22:53:30', '2021-02-19 22:53:30');
INSERT INTO `mb_block` VALUES (10018, '蝙蝠侠：阿卡姆骑士', 'http://qorvzwz4v.hn-bkt.clouddn.com/255bd9c42ab24c1ca4bb4ca2aa4421e3208650_library_hero.jpg', 10019, '1', '2021-02-19 22:54:12', '2021-02-19 22:54:12');
INSERT INTO `mb_block` VALUES (10019, '泰坦陨落2', 'http://qorvzwz4v.hn-bkt.clouddn.com/37ffa31f25854adcbde222d4501aad911237970_library_hero.jpg', 10020, '1', '2021-02-19 22:54:50', '2021-02-19 22:54:50');
INSERT INTO `mb_block` VALUES (10020, '刀塔2', 'http://qorvzwz4v.hn-bkt.clouddn.com/5b336921cd6244e1bada5a5b0091016a570_library_hero.jpg', 10021, '1', '2021-02-19 22:55:23', '2021-02-19 22:55:23');
INSERT INTO `mb_block` VALUES (10021, '植物大战僵尸', 'http://qorvzwz4v.hn-bkt.clouddn.com/45a1460826584cfb8d34fde44768a6a63590_logo.png', 10022, '1', '2021-02-19 22:56:00', '2021-02-19 22:56:00');
INSERT INTO `mb_block` VALUES (10022, '人类一败涂地', 'http://qorvzwz4v.hn-bkt.clouddn.com/884acc4156c94097bd3ce59aed4fc089477160_library_hero.jpg', 10023, '1', '2021-02-19 22:56:34', '2021-02-19 22:56:34');
INSERT INTO `mb_block` VALUES (10023, '绝地求生：大逃杀', 'http://qorvzwz4v.hn-bkt.clouddn.com/a600c72d1e424c32ad7f40bc28ba836c578080_library_hero.jpg', 10027, '1', '2021-02-20 00:36:01', '2021-02-20 00:36:01');

-- ----------------------------
-- Table structure for mb_comment
-- ----------------------------
DROP TABLE IF EXISTS `mb_comment`;
CREATE TABLE `mb_comment`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '记录状态，1有效，0无效',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `post_id` bigint(11) NULL DEFAULT NULL COMMENT '帖子ID，如果type=TC，该字段必填',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '用户ID，如果type=RC，该字段必填',
  `game_id` bigint(11) NULL DEFAULT NULL COMMENT '游戏ID，如果type=GC，该字段必填',
  `score` decimal(18, 1) UNSIGNED NULL DEFAULT NULL COMMENT '用户对游戏的评分，如果type=GC，该字段必填',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mb_comment_cid_index`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_comment
-- ----------------------------
INSERT INTO `mb_comment` VALUES (10190, '1', '有点好玩', NULL, 10037, 10023, 5.0, '2021-02-17 12:42:48', '2021-02-20 19:05:52');
INSERT INTO `mb_comment` VALUES (10191, '1', '不会玩就去学习', 10151, 10037, NULL, NULL, '2021-02-18 12:44:38', '2021-02-20 19:05:56');
INSERT INTO `mb_comment` VALUES (10192, '1', '再一次评论', NULL, 10037, 10023, 5.0, '2021-02-19 12:49:21', '2021-02-20 19:05:59');
INSERT INTO `mb_comment` VALUES (10193, '1', '可以评论多次', NULL, 10037, 10023, 5.0, '2021-02-20 12:49:42', '2021-02-20 19:06:04');
INSERT INTO `mb_comment` VALUES (10194, '1', '帖子不能删除和修改', 10150, 10037, NULL, NULL, '2021-02-20 12:50:35', '2021-02-20 12:50:35');
INSERT INTO `mb_comment` VALUES (10195, '1', '我看不到你的签名', 10150, 10037, NULL, NULL, '2021-02-20 12:50:50', '2021-02-20 12:50:50');
INSERT INTO `mb_comment` VALUES (10196, '1', '世界上最好玩的游戏5555', NULL, 10038, 10018, 5.0, '2021-02-20 13:21:17', '2021-02-20 13:21:17');
INSERT INTO `mb_comment` VALUES (10197, '1', '让我康康', 10152, 10036, NULL, NULL, '2021-02-20 13:26:08', '2021-02-20 13:26:08');
INSERT INTO `mb_comment` VALUES (10198, '1', '不错，联机打怪很顺畅', NULL, 10035, 10010, 5.0, '2021-02-20 18:29:02', '2021-02-20 19:06:09');
INSERT INTO `mb_comment` VALUES (10199, '1', '据说刷不了评分了，呜呜呜', NULL, 10036, 10010, 5.0, '2021-02-20 18:30:27', '2021-02-20 18:30:27');
INSERT INTO `mb_comment` VALUES (10200, '1', '鸡哥', 10154, 10037, NULL, NULL, '2021-02-20 19:10:42', '2021-02-20 19:10:42');
INSERT INTO `mb_comment` VALUES (10201, '1', '我要刷', NULL, 10037, 10010, 5.0, '2021-02-20 19:13:27', '2021-02-20 19:13:27');

-- ----------------------------
-- Table structure for mb_game
-- ----------------------------
DROP TABLE IF EXISTS `mb_game`;
CREATE TABLE `mb_game`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增，默认从10000开始自增，建表时候指定，唯一标识 ',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏中文名',
  `price` decimal(18, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '游戏价格，保留两位小数，注意默认是0.00，否则会在加减乘除上带来转换问题',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '记录状态，1有效，0无效',
  `score` decimal(11, 1) UNSIGNED NOT NULL DEFAULT 0.0 COMMENT '游戏评分，最大5分，保留一位小数，算法为所有评分总和除以评分人数=评分',
  `description` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏简介',
  `release_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `developer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '开发商',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发行商',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `origin_price` decimal(18, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '游戏原价',
  `photo_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏封面大图',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mb_game_gid_index`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10028 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_game
-- ----------------------------
INSERT INTO `mb_game` VALUES (10010, 'Monster Hunter: World', 203.00, '1', 5.0, '新的生命之地。狩猎, 就是本能! 在系列最新作品「Monster Hunter: World」中,玩家可以体验终极的狩猎生活,活用新建构的世界中各种各样的地形与生态环境享受狩猎的惊喜与兴奋。', '2018-08-10 00:00:00', 'CAPCOM Co., Ltd.', 'CAPCOM Co., Ltd.', '2021-02-19 19:13:57', '2021-02-20 18:30:27', 406.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/3e4150203b2f4c86af0e484f6c3cedc8201706131605508504.jpg');
INSERT INTO `mb_game` VALUES (10011, 'Tom Clancy\'s Rainbow Six® Siege', 118.00, '1', 4.3, '《彩虹六号：围攻》是育碧蒙特利尔工作室旗下即将推出的知名第一人称射击模拟系列游戏的最新作品，专为新一代游戏机和主机开发，属于《彩虹六号》系列。该作灵感来源于现实世界中的反恐行动，《彩虹六号：围攻》诚邀玩家掌控破坏的艺术，游戏核心是对激烈的近距离对抗，高杀伤力，战术，团队合作。', '2015-12-02 00:00:00', 'Ubisoft Montreal', 'Ubisoft', '2021-02-19 22:05:16', '2021-02-20 01:55:30', 348.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/66cf05852f0041adaecda10c91625d5e359550_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10012, '巫师 3：狂猎', 127.00, '1', 4.5, '随着北方领域战乱四起，您接下了此生最为重大的一笔委托：找到预言之子，一件足以改变世界面貌的活生生的武器。', '2015-05-18 00:00:00', 'CD PROJEKT RED', 'CD PROJEKT RED', '2021-02-19 22:07:25', '2021-02-20 01:55:30', 158.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/422c69f9d2d84ba4ba8f69710616b0ea292030_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10013, 'Stardew Valley', 48.00, '1', 5.0, '你继承了你爷爷在星露谷留下的老旧农场。带着爷爷留下的残旧工具和几枚硬币开始了你的新生活。你能适应这小镇上的生活并且将这个杂草丛生的老旧农场变成一个繁荣的家吗？', '2016-02-27 00:00:00', ' ConcernedApe', ' ConcernedApe', '2021-02-19 22:08:48', '2021-02-20 01:55:30', 48.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/bc0fbd86f4df4cbf80f8222e757bee0bss_b887651a93b0525739049eb4194f633de2df75be.jpg');
INSERT INTO `mb_game` VALUES (10014, '赛博朋克 2077', 298.00, '1', 3.0, '《赛博朋克 2077》是一款开放世界游戏，故事发生在夜之城，权力更迭和身体改造是这里不变的主题。扮演一名野心勃勃的雇佣兵：V，追寻一种独一无二的植入体——获得永生的关键。自定义角色义体、技能和玩法，探索包罗万象的城市。您做出的选择也将会对剧情和周遭世界产生影响。', '2020-12-10 00:00:00', ' CD PROJEKT RED', ' CD PROJEKT RED', '2021-02-19 22:10:26', '2021-02-20 01:55:30', 298.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/0219032f1422402b8dc9afdea7fb4b671091500_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10015, '戴森球计划', 70.00, '1', 4.8, '浩瀚无垠的宇宙，未知的征程，作为戴森球计划的一名工程师，你将前往陌生的星系，建造戴森球。从一无所有白手起家，采集资源，规划设计生产线，逐步实现全自动化，将你的工厂从一个小作坊，发展成为庞大的跨星系工业帝国。探索未知的星球，发现珍奇，让你的足迹遍布星辰大海。', '2021-01-21 00:00:00', ' Youthcat Studio', ' Gamera Game', '2021-02-19 22:11:57', '2021-02-20 01:48:10', 70.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/26d6a5561edc482086a26eff77e38fa81366540_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10016, 'Euro Truck Simulator 2', 99.00, '1', 4.5, '像公路之王一样在欧洲穿行， 将价值不菲的货物完美送抵远方！往返于英国，比利时，德国，意大利，荷兰，波兰等众多城市；您的耐力，技巧，速度都被您发挥到了极致！', '2012-10-18 00:00:00', ' SCS Software', ' SCS Software', '2021-02-19 22:13:31', '2021-02-20 01:55:30', 167.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/9f10061a656047de93402cad174d5e19227300_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10017, '太吾绘卷 The Scroll Of Taiwu', 68.00, '1', 4.1, '《太吾绘卷》是一款以神话和武侠为题材的独立游戏。玩家将扮演神秘的“太吾氏传人”，在以古代中华神州为背景的架空世界中，通过一代又一代传人的努力和牺牲，最终击败强大的宿敌，决定人世的命运。', '2018-09-02 00:00:00', ' ConchShip Games', ' ConchShip Games', '2021-02-19 22:14:44', '2021-02-20 01:55:30', 68.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/1d016805f2c24e1daa1d8a7bbd894e35838350_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10018, 'Slay the Spire', 80.00, '1', 5.0, '我们将卡牌游戏与Roguelike游戏融为一体，制作出了这款最棒的单机组牌游戏。打造出独一无二的牌组、遇见无数离奇的生物、发现威力强大的遗物、去屠戮这座高塔吧！', '2019-01-23 00:00:00', ' Mega Crit Games', ' Mega Crit Games', '2021-02-19 22:17:00', '2021-02-20 01:55:30', 80.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/422fe89ae75546d390b41dd695637c39646570_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10019, 'Batman™: Arkham Knight', 17.00, '1', 4.3, '《蝙蝠侠™：阿卡姆骑士》将 Rocksteady Studios 获奖的“阿卡姆”三部曲带入史诗般的结局。《蝙蝠侠：阿卡姆骑士》新世代平台独占游戏，将有 Rocksteady 专门设计的蝙蝠车。', '2015-06-03 00:00:00', ' Rocksteady Studios', ' Warner Bros. Interactive Entertainment', '2021-02-19 22:19:24', '2021-02-20 01:55:30', 163.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/9d6bd207c43e4b1d9ee56694e1a6c22c208650_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10020, 'Titanfall® 2', 52.14, '1', 4.6, '跃升起始包内含所有数字豪华版内容，让您快速升级和解锁物品，还有 R-201 卡宾枪的地下定制武器皮肤。', '2016-10-28 00:00:00', ' Respawn Entertainment', ' Electronic Arts', '2021-02-19 22:20:40', '2021-02-20 01:55:30', 214.20, 'http://qorvzwz4v.hn-bkt.clouddn.com/f41638c91e724444a6a3afd2b137facc1237970_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10021, 'Dota 2', 0.00, '1', 4.2, '每一天全球有数百万玩家化为一百余名Dota英雄展开大战。不论是游戏时间刚满10小时还是1000小时，比赛中总能找到新鲜感。定期的更新则保证游戏性、功能和英雄都能持续发展，Dota 2已真正地焕发了生命。', '2013-07-10 00:00:00', ' Valve', ' Valve', '2021-02-19 22:23:37', '2021-02-20 01:55:30', 0.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/8f1c71e21a334900975e12167daebafb570_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10022, 'Plants vs. Zombies GOTY Edition', 21.00, '1', 5.0, '来自曾开发过宝石迷阵和幻幻球的宝开公司的全新动作策略游戏！ 可怕的僵尸即将入侵你的家，唯一的防御方式就是你栽种的植物！武装你的植物，切换他们不同的功能，诸如强悍的豌豆射手或樱桃炸弹，更加快速有效的将僵尸阻挡在入侵的道路上。不同的敌人，不同的玩法构成五种不同的游戏模式。', '2009-05-06 00:00:00', ' PopCap Games, Inc.', ' PopCap Games, Inc., Electronic Arts', '2021-02-19 22:26:05', '2021-02-20 01:55:30', 84.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/94c58fb0ace3474aac4432c73c384fb23590_logo.png');
INSERT INTO `mb_game` VALUES (10023, '人类一败涂地 / Human Fall Flat', 19.00, '1', 5.0, '《人类一败涂地》是一款轻松搞笑的沙雕游戏，游戏的场景发生在飘渺的梦境之中。玩家既可以单机自闭，也可以最多八人联机一起欢畅。游戏支持的创意工坊，还可以让大家免费玩到不计其数的高质量玩家关卡和皮肤。游戏支持中文。', '2016-07-23 00:00:00', ' No Brakes Games', ' Curve Digital', '2021-02-19 22:27:58', '2021-02-20 12:49:21', 35.20, 'http://qorvzwz4v.hn-bkt.clouddn.com/3e2511dca77d459095a88ccc1bc013d7477160_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10024, 'Streets of Rogue', 70.00, '1', 4.8, '在随机生成的城市中战斗、潜行，一路披荆斩棘。这款游戏就像是《废土之王》遇上《杀出重围》，再加上一点《侠盗猎车手》的无政府主义。Rogue-lite 风格结合模拟经营，然后彻底陷入疯狂。', '2019-07-13 00:00:00', ' Matt Dabrowski', ' tinyBuild', '2021-02-19 22:33:55', '2021-02-20 01:48:10', 125.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/575b851fed9d4e9b9d637e5b67a23dc6512900_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10025, 'Shadow Tactics: Blades of the Shogun', 115.00, '1', 4.8, '1615年，日本处在幕府时代。一个新的幕府将军获得了统治日本的权力，而且极力地维护和平。为了与阴谋和叛变势力作斗争，他招募了五位专门从事暗杀、蓄意破坏和间谍活动的人才，他们在其擅长领域上均身怀绝技。', '2016-12-06 00:00:00', ' Mimimi Games', ' Daedalic Entertainment', '2021-02-19 22:35:40', '2021-02-20 01:48:10', 176.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/785577bf66774fd5a80097daaa5a71f2418240_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10026, '命运2', 0.00, '1', 4.3, '命运2是发生在一个独立进化的世界中的一款免费的大型多人线上动作游戏。你可以在任何时间与任何地点和你的朋友们进行游戏。', '2019-10-01 00:00:00', ' Bungie', ' Bungie', '2021-02-19 22:36:59', '2021-02-20 01:55:30', 0.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/305a2ab7c784408d9d77fb0de5c47cdd1085660_library_hero.jpg');
INSERT INTO `mb_game` VALUES (10027, 'PLAYERUNKNOWN\'S BATTLEGROUNDS', 98.00, '1', 3.0, '绝地求生(PLAYERUNKNOWN’S BATTLEGROUNDS)是战术竞技类型的游戏，每一局游戏将有100名玩家参与，他们将被投放在绝地岛(battlegrounds)的上空，游戏开始跳伞时所有人都一无所有。', '2017-12-20 00:00:00', ' KRAFTON, Inc.', ' KRAFTON, Inc.', '2021-02-20 00:35:42', '2021-02-20 01:55:30', 98.00, 'http://qorvzwz4v.hn-bkt.clouddn.com/93bba0f5c91145d4b5aa0a1f8858aeac578080_library_hero.jpg');

-- ----------------------------
-- Table structure for mb_menu
-- ----------------------------
DROP TABLE IF EXISTS `mb_menu`;
CREATE TABLE `mb_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `menu_icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单图片，去ant的icon组件里面找，写图标名字',
  `menu_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '1可用0不可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_menu
-- ----------------------------
INSERT INTO `mb_menu` VALUES (1, '首页', 'home', '/home', '1');
INSERT INTO `mb_menu` VALUES (3, '帖子管理', 'file-markdown', '/postList', '1');
INSERT INTO `mb_menu` VALUES (4, '板块管理', 'block', '/blockList', '1');
INSERT INTO `mb_menu` VALUES (5, '菜单管理', 'menu', '/menuList', '1');
INSERT INTO `mb_menu` VALUES (6, '用户管理', 'user', '/userList', '1');
INSERT INTO `mb_menu` VALUES (7, '游戏管理', 'robot', '/gameList', '1');
INSERT INTO `mb_menu` VALUES (15, '标签管理', 'tags', '/tagList', '1');
INSERT INTO `mb_menu` VALUES (16, '游戏图片管理', 'picture', '/photoList', '1');

-- ----------------------------
-- Table structure for mb_order
-- ----------------------------
DROP TABLE IF EXISTS `mb_order`;
CREATE TABLE `mb_order`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` bigint(11) UNSIGNED NOT NULL COMMENT '订单号',
  `game_id` bigint(11) UNSIGNED NOT NULL COMMENT '游戏id',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '订单更新时间',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '数据状态，1可用，0禁用',
  `success_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单是否交易成功1成功0失败',
  `order_cost` decimal(10, 2) UNSIGNED NOT NULL COMMENT '交易金额',
  `user_id` bigint(11) UNSIGNED NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_order
-- ----------------------------
INSERT INTO `mb_order` VALUES (74, 6188266, 10010, '2021-02-20 01:20:23', '2021-02-20 01:20:23', '1', '1', 203.00, 10035);
INSERT INTO `mb_order` VALUES (75, 4868750, 10010, '2021-02-20 01:48:02', '2021-02-20 01:48:02', '1', '1', 203.00, 10036);
INSERT INTO `mb_order` VALUES (76, 1567752, 10011, '2021-02-20 11:56:30', '2021-02-20 11:56:30', '1', '1', 118.00, 10036);
INSERT INTO `mb_order` VALUES (77, 6201486, 10012, '2021-02-20 11:58:52', '2021-02-20 11:58:52', '1', '1', 127.00, 10036);
INSERT INTO `mb_order` VALUES (78, 8465778, 10023, '2021-02-20 12:42:36', '2021-02-20 12:42:36', '1', '1', 19.00, 10037);
INSERT INTO `mb_order` VALUES (79, 7551244, 10018, '2021-02-20 13:19:07', '2021-02-20 13:19:07', '1', '1', 80.00, 10038);
INSERT INTO `mb_order` VALUES (80, 1271327, 10018, '2021-02-20 13:47:01', '2021-02-20 13:47:01', '1', '1', 80.00, 10036);
INSERT INTO `mb_order` VALUES (81, 6553581, 10010, '2021-02-20 13:49:24', '2021-02-20 13:49:24', '1', '1', 203.00, 10038);
INSERT INTO `mb_order` VALUES (82, 2665136, 10010, '2021-02-20 19:13:15', '2021-02-20 19:13:15', '1', '1', 203.00, 10037);

-- ----------------------------
-- Table structure for mb_photo
-- ----------------------------
DROP TABLE IF EXISTS `mb_photo`;
CREATE TABLE `mb_photo`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `photo_link` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片链接',
  `game_id` bigint(11) UNSIGNED NULL DEFAULT NULL COMMENT '游戏ID',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '状态，记录该条状态是否有效,1有效，0无效',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10148 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_photo
-- ----------------------------
INSERT INTO `mb_photo` VALUES (10094, 'http://qorvzwz4v.hn-bkt.clouddn.com/1e663cacea4c43dcab8bd25c06601a9dss_669f9008f708c19fe3c41d647516f7a73bf26d24.jpg', 10010, '1', '2021-02-20 00:31:43', '2021-02-20 00:31:43');
INSERT INTO `mb_photo` VALUES (10095, 'http://qorvzwz4v.hn-bkt.clouddn.com/72e2aaba08374ea6b5209575910ce0b4ss_681cc5358ec55a997aee9f757ffe8b418dc79a32.jpg', 10010, '1', '2021-02-20 00:32:53', '2021-02-20 00:32:53');
INSERT INTO `mb_photo` VALUES (10096, 'http://qorvzwz4v.hn-bkt.clouddn.com/0d4072933aa04d828ee859c9388715c4ss_6d4e3b1021a489106f33c054bcb0e74ea4bd706f.jpg', 10011, '1', '2021-02-20 00:34:17', '2021-02-20 00:34:17');
INSERT INTO `mb_photo` VALUES (10097, 'http://qorvzwz4v.hn-bkt.clouddn.com/68d4c2e5cc7345f09bd6ed7c8fd27b20ss_0d619a68898400c9cd59b1afa2e36bd50f12d0b5.jpg', 10011, '1', '2021-02-20 00:34:24', '2021-02-20 00:34:24');
INSERT INTO `mb_photo` VALUES (10098, 'http://qorvzwz4v.hn-bkt.clouddn.com/4c17a27d98b34f83b5a48375c002e212ss_01dbd675c184301a53fad53431dcf062a07aae77.jpg', 10011, '1', '2021-02-20 00:34:35', '2021-02-20 00:34:35');
INSERT INTO `mb_photo` VALUES (10099, 'http://qorvzwz4v.hn-bkt.clouddn.com/28338f8dc0c743a2a8d11c5346c4ad80ss_107600c1337accc09104f7a8aa7f275f23cad096.jpg', 10012, '1', '2021-02-20 00:36:48', '2021-02-20 00:36:48');
INSERT INTO `mb_photo` VALUES (10100, 'http://qorvzwz4v.hn-bkt.clouddn.com/537c0af2188c4703940dbc4f222050b8ss_eda99e7f705a113d04ab2a7a36068f3e7b343d17.jpg', 10012, '1', '2021-02-20 00:37:23', '2021-02-20 00:37:23');
INSERT INTO `mb_photo` VALUES (10101, 'http://qorvzwz4v.hn-bkt.clouddn.com/45de46b4eab94202896e4feb59c3367bss_64eb760f9a2b67f6731a71cce3a8fb684b9af267.jpg', 10012, '1', '2021-02-20 00:37:31', '2021-02-20 00:37:31');
INSERT INTO `mb_photo` VALUES (10102, 'http://qorvzwz4v.hn-bkt.clouddn.com/dba550ea4f6a41bfaac7190a86a7e687ss_4fa0866709ede3753fdf2745349b528d5e8c4054.jpg', 10013, '1', '2021-02-20 00:38:18', '2021-02-20 00:38:18');
INSERT INTO `mb_photo` VALUES (10103, 'http://qorvzwz4v.hn-bkt.clouddn.com/11d019438f5d4a079ab24209fa9a3deess_9ac899fe2cda15d48b0549bba77ef8c4a090a71c.jpg', 10013, '1', '2021-02-20 00:38:22', '2021-02-20 00:38:22');
INSERT INTO `mb_photo` VALUES (10104, 'http://qorvzwz4v.hn-bkt.clouddn.com/2fe300d701e14f3b87a5a73afc94b6b8ss_d836f0a5b0447fb6a2bdb0a6ac5f954949d3c41e.jpg', 10013, '1', '2021-02-20 00:38:29', '2021-02-20 00:38:29');
INSERT INTO `mb_photo` VALUES (10105, 'http://qorvzwz4v.hn-bkt.clouddn.com/9adbb1756b784b1c90fc87ab1aaf7773ss_9beef14102f164fa1163536d0fb3a51d0a2e4a3f.jpg', 10014, '1', '2021-02-20 00:39:21', '2021-02-20 00:39:21');
INSERT INTO `mb_photo` VALUES (10106, 'http://qorvzwz4v.hn-bkt.clouddn.com/27bc5a783f34409c920caf96f46da12fss_ae4465fa8a44dd330dbeb7992ba196c2f32cabb1.jpg', 10014, '1', '2021-02-20 00:39:27', '2021-02-20 00:39:27');
INSERT INTO `mb_photo` VALUES (10107, 'http://qorvzwz4v.hn-bkt.clouddn.com/617473e2404b400c8cf37560903ef7e9ss_b20689e73e3ac19bcc5fad2c18d0353c769de144.jpg', 10014, '1', '2021-02-20 00:39:30', '2021-02-20 00:39:30');
INSERT INTO `mb_photo` VALUES (10108, 'http://qorvzwz4v.hn-bkt.clouddn.com/58b7556d2fdf4f7b86ea861e2799a840ss_36242c2930a50355982a0da128f37e23e25a1047.jpg', 10015, '1', '2021-02-20 00:40:29', '2021-02-20 00:40:29');
INSERT INTO `mb_photo` VALUES (10109, 'http://qorvzwz4v.hn-bkt.clouddn.com/e3d212c0e5ce467eb727d2165f04603ass_ca1c8ba11e539cbeb8f4d91f6685b0fa01ddf705.jpg', 10015, '1', '2021-02-20 00:40:32', '2021-02-20 00:40:32');
INSERT INTO `mb_photo` VALUES (10110, 'http://qorvzwz4v.hn-bkt.clouddn.com/c22302e6c6694f7b96ec1ed35fe57e8ess_1f699b2ba7ce5025064050cf5498f6a576b89d31.jpg', 10015, '1', '2021-02-20 00:40:40', '2021-02-20 00:40:40');
INSERT INTO `mb_photo` VALUES (10111, 'http://qorvzwz4v.hn-bkt.clouddn.com/a74845a7b4e94d138023a84ff35c4039ss_cc63338eb8d72bdbf10a5a51aef6275938bd8ba9.jpg', 10016, '1', '2021-02-20 00:41:41', '2021-02-20 00:41:41');
INSERT INTO `mb_photo` VALUES (10112, 'http://qorvzwz4v.hn-bkt.clouddn.com/df765fe0c16247ad85b0aacf3ff59286ss_f30c62166abbbb87b1c4a822f46b538d84978816.jpg', 10016, '1', '2021-02-20 00:41:45', '2021-02-20 00:41:45');
INSERT INTO `mb_photo` VALUES (10113, 'http://qorvzwz4v.hn-bkt.clouddn.com/93c0d1a6086744b1bfd6e023f6932e72ss_cf79030615e1188ed7e31f36c6b4dcf8b5cf4512.jpg', 10016, '1', '2021-02-20 00:41:49', '2021-02-20 00:41:49');
INSERT INTO `mb_photo` VALUES (10114, 'http://qorvzwz4v.hn-bkt.clouddn.com/f2e0e2d3a97b4aee936ef545aad4ed39ss_634fde09404a37a1012d42e93eb1db3708a2a2ca.jpg', 10017, '1', '2021-02-20 00:43:52', '2021-02-20 00:43:52');
INSERT INTO `mb_photo` VALUES (10115, 'http://qorvzwz4v.hn-bkt.clouddn.com/8f65c2ad876649e7bde0389469cabff3ss_db6538700ac2190f5904511b8af28be3641bc810.jpg', 10017, '1', '2021-02-20 00:44:06', '2021-02-20 00:44:06');
INSERT INTO `mb_photo` VALUES (10116, 'http://qorvzwz4v.hn-bkt.clouddn.com/5b0bf594189c46669800758d7b63d725ss_dbaae76a64f626121c33d846831a4fd6d02f947f.jpg', 10017, '1', '2021-02-20 00:44:09', '2021-02-20 00:44:09');
INSERT INTO `mb_photo` VALUES (10117, 'http://qorvzwz4v.hn-bkt.clouddn.com/2a146bf1b055491d88fceee123dc1100ss_5b2b32f09da5a5bed99d5c5168b50fd681a4690c.jpg', 10018, '1', '2021-02-20 00:44:49', '2021-02-20 00:44:49');
INSERT INTO `mb_photo` VALUES (10118, 'http://qorvzwz4v.hn-bkt.clouddn.com/8e5059adafed40e391b8295bca99edccss_1299d7fe55771cf564848c5046fbef7936178440.jpg', 10018, '1', '2021-02-20 00:44:57', '2021-02-20 00:44:57');
INSERT INTO `mb_photo` VALUES (10119, 'http://qorvzwz4v.hn-bkt.clouddn.com/a0bb411566ef47ba8141a29f1761fa3bss_b757436d08ba08292796bfed9c60e7cc99d5f2c3.jpg', 10018, '1', '2021-02-20 00:45:00', '2021-02-20 00:45:00');
INSERT INTO `mb_photo` VALUES (10120, 'http://qorvzwz4v.hn-bkt.clouddn.com/a0bb411566ef47ba8141a29f1761fa3bss_b757436d08ba08292796bfed9c60e7cc99d5f2c3.jpg', 10019, '1', '2021-02-20 00:45:59', '2021-02-20 00:45:59');
INSERT INTO `mb_photo` VALUES (10121, 'http://qorvzwz4v.hn-bkt.clouddn.com/3ed2ef89a30048efb0acd8d2376a5a7fss_90026e46a995760d53bfa2dc1612b73960c84c94.jpg', 10019, '1', '2021-02-20 00:46:07', '2021-02-20 00:46:07');
INSERT INTO `mb_photo` VALUES (10122, 'http://qorvzwz4v.hn-bkt.clouddn.com/a79bcbb23c2b453291e4fa0191775fcess_5da3185e4f7da999300555747be6b84b8f16164f.jpg', 10019, '1', '2021-02-20 00:46:15', '2021-02-20 00:46:15');
INSERT INTO `mb_photo` VALUES (10123, 'http://qorvzwz4v.hn-bkt.clouddn.com/0e045e7591414799a0e6443223ba61e8ss_18550193debe4e3461e9f5daac9eb4e399dcebab.jpg', 10020, '1', '2021-02-20 00:46:59', '2021-02-20 00:46:59');
INSERT INTO `mb_photo` VALUES (10124, 'http://qorvzwz4v.hn-bkt.clouddn.com/4a647eba8d3d4f2aa2c108f858524152ss_f4a8464ce43962b76fa6f2156b341eee28ad6494.jpg', 10020, '1', '2021-02-20 00:47:04', '2021-02-20 00:47:04');
INSERT INTO `mb_photo` VALUES (10125, 'http://qorvzwz4v.hn-bkt.clouddn.com/46881c31a7de45409d36f240d4a5426bss_9ed56a85aef47554156999dfbd4091d225da2a47.jpg', 10020, '1', '2021-02-20 00:47:07', '2021-02-20 00:47:07');
INSERT INTO `mb_photo` VALUES (10126, 'http://qorvzwz4v.hn-bkt.clouddn.com/0ff72904f31745d79e52023fd836bb6ass_7ab506679d42bfc0c0e40639887176494e0466d9.jpg', 10021, '1', '2021-02-20 00:47:58', '2021-02-20 00:47:58');
INSERT INTO `mb_photo` VALUES (10127, 'http://qorvzwz4v.hn-bkt.clouddn.com/68e5ce4d664b4f3fa307d02f6f958cb0ss_ad8eee787704745ccdecdfde3a5cd2733704898d.jpg', 10021, '1', '2021-02-20 00:48:02', '2021-02-20 00:48:02');
INSERT INTO `mb_photo` VALUES (10128, 'http://qorvzwz4v.hn-bkt.clouddn.com/93581ca1f8f84e409333e3cf3352780ass_86d675fdc73ba10462abb8f5ece7791c5047072c.jpg', 10021, '1', '2021-02-20 00:48:06', '2021-02-20 00:48:06');
INSERT INTO `mb_photo` VALUES (10129, 'http://qorvzwz4v.hn-bkt.clouddn.com/dd194875149e4fc9876317d305d3bc170000008150.jpg', 10022, '1', '2021-02-20 00:48:53', '2021-02-20 00:48:53');
INSERT INTO `mb_photo` VALUES (10130, 'http://qorvzwz4v.hn-bkt.clouddn.com/71f738dbf8584ecaab81b16774cd510d0000008151.jpg', 10022, '1', '2021-02-20 00:48:56', '2021-02-20 00:48:56');
INSERT INTO `mb_photo` VALUES (10131, 'http://qorvzwz4v.hn-bkt.clouddn.com/407fa4218cdb40fe9283d4f00bc689160000008152.jpg', 10022, '1', '2021-02-20 00:49:00', '2021-02-20 00:49:00');
INSERT INTO `mb_photo` VALUES (10132, 'http://qorvzwz4v.hn-bkt.clouddn.com/0e6116c72b7f45b9b66ccc1fd72eff3bss_5028511e8ff9d72f2130f88d0b32802197fa456d.jpg', 10023, '1', '2021-02-20 00:49:38', '2021-02-20 00:49:38');
INSERT INTO `mb_photo` VALUES (10133, 'http://qorvzwz4v.hn-bkt.clouddn.com/24ad5822f9f8469e857017121cd02a5ess_5e5efb54c98b5c09bcb0272f73e3a275cffb1a4b.jpg', 10023, '1', '2021-02-20 00:49:44', '2021-02-20 00:49:44');
INSERT INTO `mb_photo` VALUES (10134, 'http://qorvzwz4v.hn-bkt.clouddn.com/137489a68cf94fb191b05d44f1b7ed74ss_83cacb5969f28b6612f0a6649b40957c998f7db8.jpg', 10023, '1', '2021-02-20 00:49:47', '2021-02-20 00:49:47');
INSERT INTO `mb_photo` VALUES (10135, 'http://qorvzwz4v.hn-bkt.clouddn.com/47fcd2eac70046dbb716de75242821b9ss_80291e409b1ba92e59a9bc2c66bf3105efae90fa.jpg', 10024, '1', '2021-02-20 00:50:42', '2021-02-20 00:50:42');
INSERT INTO `mb_photo` VALUES (10136, 'http://qorvzwz4v.hn-bkt.clouddn.com/0d28b979c5e14fb2bc4b67ae40a680d2ss_8122938569ac3e6caa7e327baa186504ae7556ad.jpg', 10024, '1', '2021-02-20 00:50:48', '2021-02-20 00:50:48');
INSERT INTO `mb_photo` VALUES (10137, 'http://qorvzwz4v.hn-bkt.clouddn.com/8b86cc87ffb44e51bef1199d50ae3e57ss_9fc33552a95fada32c6bc0a75b6d899244c4779c.jpg', 10024, '1', '2021-02-20 00:50:51', '2021-02-20 00:50:51');
INSERT INTO `mb_photo` VALUES (10138, 'http://qorvzwz4v.hn-bkt.clouddn.com/d03db38b354340f09194f1e994d4a5fdss_2ba39b8a5b0337e9436e393d0b166865b275fd31.jpg', 10025, '1', '2021-02-20 00:51:33', '2021-02-20 00:51:33');
INSERT INTO `mb_photo` VALUES (10139, 'http://qorvzwz4v.hn-bkt.clouddn.com/3b658734dd17489a99db46f489976690ss_410b4a15c97f1d27ab2e2e86bc7a1ffa1d175f3f.jpg', 10025, '1', '2021-02-20 00:51:36', '2021-02-20 00:51:36');
INSERT INTO `mb_photo` VALUES (10140, 'http://qorvzwz4v.hn-bkt.clouddn.com/5ab14e2a6f6e45a3b02e7b962636d741ss_e81ca5a0bf80bf851b48747c23ddf01add0ba202.jpg', 10025, '1', '2021-02-20 00:51:40', '2021-02-20 00:51:40');
INSERT INTO `mb_photo` VALUES (10141, 'http://qorvzwz4v.hn-bkt.clouddn.com/02e5b011f79f45baa4b1cca2a4f14350ss_01fdd090ed1dd70112ce2c6d6fc208df0a008ac7.jpg', 10026, '1', '2021-02-20 00:52:24', '2021-02-20 00:52:24');
INSERT INTO `mb_photo` VALUES (10142, 'http://qorvzwz4v.hn-bkt.clouddn.com/ffcb6fa278884aa987f9cf8d78c9567ass_d923711c0eb833b1df8607fa3df4dcebbe470cf2.jpg', 10026, '1', '2021-02-20 00:52:28', '2021-02-20 00:52:28');
INSERT INTO `mb_photo` VALUES (10143, 'http://qorvzwz4v.hn-bkt.clouddn.com/de28e81370be4c4699acd70ae5a8b4b5ss_7fcc82f468fcf8278c7ffa95cebf949bfc6845fc.jpg', 10026, '1', '2021-02-20 00:52:31', '2021-02-20 00:52:31');
INSERT INTO `mb_photo` VALUES (10144, 'http://qorvzwz4v.hn-bkt.clouddn.com/a0fe4a966a3d4fc0aad3ffd50a1ec842ss_23af2e59855a833c22d0c11ca23a719f54a554ff.jpg', 10027, '1', '2021-02-20 00:53:39', '2021-02-20 00:53:39');
INSERT INTO `mb_photo` VALUES (10145, 'http://qorvzwz4v.hn-bkt.clouddn.com/3cae5bb031a2475c9ff20b22c52beefbss_8814c071f0cce53821d8e1b1a96de78d00e5d4d1.jpg', 10027, '1', '2021-02-20 00:53:42', '2021-02-20 00:53:42');
INSERT INTO `mb_photo` VALUES (10146, 'http://qorvzwz4v.hn-bkt.clouddn.com/ee367f8ea7854ece804b1601ff1ebf19ss_cec7ea5e83407dba51c80d24a2c8076e93752d4f.jpg', 10027, '1', '2021-02-20 00:53:47', '2021-02-20 00:53:47');
INSERT INTO `mb_photo` VALUES (10147, 'http://qorvzwz4v.hn-bkt.clouddn.com/42f78d644fdc4293b0566ef5c4c7cde1ss_6d26868b45c20bf4dd5f75f31264aca08ce17217.jpg', 10010, '1', '2021-02-20 13:44:46', '2021-02-20 13:44:46');

-- ----------------------------
-- Table structure for mb_post
-- ----------------------------
DROP TABLE IF EXISTS `mb_post`;
CREATE TABLE `mb_post`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `author_id` bigint(11) UNSIGNED NOT NULL COMMENT '作者ID',
  `block_id` bigint(11) UNSIGNED NOT NULL COMMENT '版块ID',
  `title` varchar(41) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帖子标题',
  `content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帖子内容',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态，记录该条状态是否有效,0有效，1无效',
  `photo_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帖子的封面图，记录的是链接地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `post_tid_index`(`id`) USING BTREE COMMENT '主键索引'
) ENGINE = InnoDB AUTO_INCREMENT = 10155 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_post
-- ----------------------------
INSERT INTO `mb_post` VALUES (10150, 10036, 10009, '煌黑龙求组！！', '![EAE41AB3F3A94E1EA2D5D73A18DC804E.jpeg](http://qorvzwz4v.hn-bkt.clouddn.com/0e59cdd550d1446fa1fe640ce174182bEAE41AB3-F3A9-4E1E-A2D5-D73A18DC804E.jpeg)\n\nrt', '2021-02-16 01:42:18', '2021-02-20 18:59:11', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/0e59cdd550d1446fa1fe640ce174182bEAE41AB3-F3A9-4E1E-A2D5-D73A18DC804E.jpeg');
INSERT INTO `mb_post` VALUES (10151, 10037, 10022, '不会玩', '![869FE04395BE43DBA5FBE4B11C3A81FE.jpeg](http://qorvzwz4v.hn-bkt.clouddn.com/403be1a3e9af46a98648080c10336173869FE043-95BE-43DB-A5FB-E4B11C3A81FE.jpeg)', '2021-02-18 12:44:03', '2021-02-20 18:59:14', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/403be1a3e9af46a98648080c10336173869FE043-95BE-43DB-A5FB-E4B11C3A81FE.jpeg');
INSERT INTO `mb_post` VALUES (10152, 10038, 10023, '让我来看看', '![imgceae82c9a352c1cf3c445777e2e8e4b2.jpg](http://qorvzwz4v.hn-bkt.clouddn.com/c4c0fbf2a5234db5b966dab0c8e1881aimg-ceae82c9a352c1cf3c445777e2e8e4b2.jpg)', '2021-02-19 13:24:35', '2021-02-20 18:59:17', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/c4c0fbf2a5234db5b966dab0c8e1881aimg-ceae82c9a352c1cf3c445777e2e8e4b2.jpg');
INSERT INTO `mb_post` VALUES (10153, 10036, 10010, '动态头像', '![F06726EB29DB432A9EE1FE4420BD7A16.jpeg](http://qorvzwz4v.hn-bkt.clouddn.com/23ae77f57ef441ca9f9d3a98111ba40bF06726EB-29DB-432A-9EE1-FE4420BD7A16.jpeg)', '2021-02-20 13:55:36', '2021-02-20 13:55:36', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/23ae77f57ef441ca9f9d3a98111ba40bF06726EB-29DB-432A-9EE1-FE4420BD7A16.jpeg');
INSERT INTO `mb_post` VALUES (10154, 10035, 10019, '听说大更新了？', '![0000008150.jpg](http://qorvzwz4v.hn-bkt.clouddn.com/1b9d60d21efe4b4bbc9a32db457009d00000008150.jpg)', '2021-02-20 15:44:41', '2021-02-20 15:44:41', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/1b9d60d21efe4b4bbc9a32db457009d00000008150.jpg');

-- ----------------------------
-- Table structure for mb_reply
-- ----------------------------
DROP TABLE IF EXISTS `mb_reply`;
CREATE TABLE `mb_reply`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '回复ID',
  `reply_target_id` bigint(11) UNSIGNED NOT NULL COMMENT '回复谁，用户ID',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '回复内容',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '回复时间',
  `user_id` bigint(11) UNSIGNED NOT NULL COMMENT '谁发表的回复',
  `comment_id` bigint(11) UNSIGNED NOT NULL COMMENT '回复了哪一条评论',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '记录是否有效，默认1有效，0无效',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mb_reply_rid_index`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10053 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_reply
-- ----------------------------
INSERT INTO `mb_reply` VALUES (10051, 10037, '我不想学习', '2021-02-20 12:44:53', 10037, 10191, '1', '2021-02-20 12:44:53');
INSERT INTO `mb_reply` VALUES (10052, 10037, '我也不想', '2021-02-20 12:47:34', 10036, 10191, '1', '2021-02-20 12:47:34');
INSERT INTO `mb_reply` VALUES (10053, 10036, '鸡哥好', '2021-02-20 19:11:07', 10037, 10197, '1', '2021-02-20 19:11:07');

-- ----------------------------
-- Table structure for mb_role
-- ----------------------------
DROP TABLE IF EXISTS `mb_role`;
CREATE TABLE `mb_role`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'USER' COMMENT '角色名称，枚举值USER | ADMIN',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '状态，记录当前记录是否有效，1有效，0无效',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_role
-- ----------------------------
INSERT INTO `mb_role` VALUES (10000, 'USER', '1', '2021-01-10 13:15:55', '2021-02-05 20:18:24');
INSERT INTO `mb_role` VALUES (10001, 'ADMIN', '1', '2021-01-10 13:15:59', '2021-02-05 20:18:24');

-- ----------------------------
-- Table structure for mb_tag
-- ----------------------------
DROP TABLE IF EXISTS `mb_tag`;
CREATE TABLE `mb_tag`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
  `game_id` bigint(11) UNSIGNED NOT NULL COMMENT '关联的游戏ID',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '状态，记录当前记录是否有效，1有效，0无效',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10065 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_tag
-- ----------------------------
INSERT INTO `mb_tag` VALUES (10010, '开放世界', 10000, '1', '2021-01-06 19:21:10', '2021-02-05 20:18:33');
INSERT INTO `mb_tag` VALUES (10011, '自由', 10001, '1', '2021-01-06 19:21:20', '2021-02-05 20:18:33');
INSERT INTO `mb_tag` VALUES (10012, '赛车', 10001, '1', '2021-01-06 19:21:24', '2021-02-05 20:18:33');
INSERT INTO `mb_tag` VALUES (10013, '休闲', 10002, '1', '2021-01-26 11:56:54', '2021-02-05 20:18:33');
INSERT INTO `mb_tag` VALUES (10014, '合作', 10010, '1', '2021-02-19 21:15:01', '2021-02-19 21:15:01');
INSERT INTO `mb_tag` VALUES (10015, '多人', 10010, '1', '2021-02-19 21:19:57', '2021-02-19 21:19:57');
INSERT INTO `mb_tag` VALUES (10016, '动作', 10010, '1', '2021-02-19 21:20:48', '2021-02-19 21:20:48');
INSERT INTO `mb_tag` VALUES (10017, '开放世界', 10010, '1', '2021-02-19 21:21:32', '2021-02-19 21:21:32');
INSERT INTO `mb_tag` VALUES (10018, '第一人称射击', 10011, '1', '2021-02-19 22:05:52', '2021-02-19 22:05:52');
INSERT INTO `mb_tag` VALUES (10019, '多人', 10011, '1', '2021-02-19 22:06:05', '2021-02-19 22:06:05');
INSERT INTO `mb_tag` VALUES (10020, '战术', 10011, '1', '2021-02-19 22:06:10', '2021-02-19 22:06:10');
INSERT INTO `mb_tag` VALUES (10021, '剧情丰富', 10012, '1', '2021-02-19 22:37:46', '2021-02-19 22:37:46');
INSERT INTO `mb_tag` VALUES (10022, '氛围', 10012, '1', '2021-02-19 22:37:54', '2021-02-19 22:37:54');
INSERT INTO `mb_tag` VALUES (10023, '角色扮演', 10012, '1', '2021-02-19 22:38:00', '2021-02-19 22:38:00');
INSERT INTO `mb_tag` VALUES (10024, '开放世界', 10012, '1', '2021-02-19 22:38:08', '2021-02-19 22:38:08');
INSERT INTO `mb_tag` VALUES (10025, '农场模拟', 10013, '1', '2021-02-19 22:38:48', '2021-02-19 22:38:48');
INSERT INTO `mb_tag` VALUES (10026, '生活模拟', 10013, '1', '2021-02-19 22:38:56', '2021-02-19 22:38:56');
INSERT INTO `mb_tag` VALUES (10027, '像素图形', 10013, '1', '2021-02-19 22:39:03', '2021-02-19 22:39:03');
INSERT INTO `mb_tag` VALUES (10028, '赛博朋克', 10014, '1', '2021-02-19 22:39:53', '2021-02-19 22:39:53');
INSERT INTO `mb_tag` VALUES (10029, '科幻', 10014, '1', '2021-02-19 22:40:00', '2021-02-19 22:40:00');
INSERT INTO `mb_tag` VALUES (10030, '未来', 10014, '1', '2021-02-19 22:40:04', '2021-02-19 22:40:04');
INSERT INTO `mb_tag` VALUES (10031, '太空', 10015, '1', '2021-02-19 22:40:25', '2021-02-19 22:40:25');
INSERT INTO `mb_tag` VALUES (10032, '自动化', 10015, '1', '2021-02-19 22:40:29', '2021-02-19 22:40:29');
INSERT INTO `mb_tag` VALUES (10033, '模拟', 10015, '1', '2021-02-19 22:40:33', '2021-02-19 22:40:33');
INSERT INTO `mb_tag` VALUES (10034, '基地建设', 10015, '1', '2021-02-19 22:40:38', '2021-02-19 22:40:38');
INSERT INTO `mb_tag` VALUES (10035, '模拟', 10016, '1', '2021-02-19 22:41:02', '2021-02-19 22:41:02');
INSERT INTO `mb_tag` VALUES (10036, '驾驶', 10016, '1', '2021-02-19 22:41:06', '2021-02-19 22:41:06');
INSERT INTO `mb_tag` VALUES (10037, '汽车模拟', 10016, '1', '2021-02-19 22:41:14', '2021-02-19 22:41:14');
INSERT INTO `mb_tag` VALUES (10038, '沙盒', 10017, '1', '2021-02-19 22:41:28', '2021-02-19 22:41:28');
INSERT INTO `mb_tag` VALUES (10039, '武术', 10017, '1', '2021-02-19 22:41:32', '2021-02-19 22:41:32');
INSERT INTO `mb_tag` VALUES (10040, '角色扮演', 10017, '1', '2021-02-19 22:41:37', '2021-02-19 22:41:37');
INSERT INTO `mb_tag` VALUES (10041, '卡牌构建类Rogue', 10018, '1', '2021-02-19 22:41:58', '2021-02-19 22:41:58');
INSERT INTO `mb_tag` VALUES (10042, '回合制', 10018, '1', '2021-02-19 22:42:07', '2021-02-19 22:42:07');
INSERT INTO `mb_tag` VALUES (10043, '动作', 10019, '1', '2021-02-19 22:42:21', '2021-02-19 22:42:21');
INSERT INTO `mb_tag` VALUES (10044, '开放世界', 10019, '1', '2021-02-19 22:42:26', '2021-02-19 22:42:26');
INSERT INTO `mb_tag` VALUES (10045, '超级英雄', 10019, '1', '2021-02-19 22:42:31', '2021-02-19 22:42:31');
INSERT INTO `mb_tag` VALUES (10046, '机甲世界', 10020, '1', '2021-02-19 22:42:53', '2021-02-19 22:42:53');
INSERT INTO `mb_tag` VALUES (10047, 'MOBA', 10021, '1', '2021-02-19 22:43:06', '2021-02-19 22:43:06');
INSERT INTO `mb_tag` VALUES (10048, '上帝视角', 10021, '1', '2021-02-19 22:43:15', '2021-02-19 22:43:15');
INSERT INTO `mb_tag` VALUES (10049, '电竞', 10021, '1', '2021-02-19 22:43:24', '2021-02-19 22:43:24');
INSERT INTO `mb_tag` VALUES (10050, '轻度Rogue', 10024, '1', '2021-02-19 22:43:42', '2021-02-19 22:43:42');
INSERT INTO `mb_tag` VALUES (10051, '动作类Rogue', 10024, '1', '2021-02-19 22:43:53', '2021-02-19 22:43:53');
INSERT INTO `mb_tag` VALUES (10052, '潜行', 10025, '1', '2021-02-19 22:44:08', '2021-02-19 22:44:08');
INSERT INTO `mb_tag` VALUES (10053, '策略', 10025, '1', '2021-02-19 22:44:13', '2021-02-19 22:44:13');
INSERT INTO `mb_tag` VALUES (10054, '战术', 10025, '1', '2021-02-19 22:44:18', '2021-02-19 22:44:18');
INSERT INTO `mb_tag` VALUES (10055, '忍者', 10025, '1', '2021-02-19 22:44:23', '2021-02-19 22:44:23');
INSERT INTO `mb_tag` VALUES (10056, '塔防', 10022, '1', '2021-02-19 22:44:45', '2021-02-19 22:44:45');
INSERT INTO `mb_tag` VALUES (10057, '僵尸', 10022, '1', '2021-02-19 22:44:49', '2021-02-19 22:44:49');
INSERT INTO `mb_tag` VALUES (10058, '休闲', 10022, '1', '2021-02-19 22:44:54', '2021-02-19 22:44:54');
INSERT INTO `mb_tag` VALUES (10059, '欢乐', 10023, '1', '2021-02-19 22:45:14', '2021-02-19 22:45:14');
INSERT INTO `mb_tag` VALUES (10060, '多人', 10023, '1', '2021-02-19 22:45:18', '2021-02-19 22:45:18');
INSERT INTO `mb_tag` VALUES (10061, '合作', 10023, '1', '2021-02-19 22:45:23', '2021-02-19 22:45:23');
INSERT INTO `mb_tag` VALUES (10062, '玩家对战', 10026, '1', '2021-02-19 22:45:57', '2021-02-19 22:45:57');
INSERT INTO `mb_tag` VALUES (10063, '刷宝射击游戏', 10026, '1', '2021-02-19 22:46:06', '2021-02-19 22:46:06');
INSERT INTO `mb_tag` VALUES (10064, '开放世界', 10026, '1', '2021-02-19 22:46:12', '2021-02-19 22:46:12');

-- ----------------------------
-- Table structure for mb_user
-- ----------------------------
DROP TABLE IF EXISTS `mb_user`;
CREATE TABLE `mb_user`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名，本系统是邮箱，需要程序校验',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码，MD5加密',
  `description` varchar(41) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '个人简介',
  `user_state` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户状态，枚举 默认是NORMAL（正常），INVALID（失效），BANNED（非法，被封禁）',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户创建日期',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '字段更新时间，修改该条记录则自动更新这个字段',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '状态，记录当前记录是否有效，1有效，0无效',
  `photo_link` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户头像链接',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10040 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_user
-- ----------------------------
INSERT INTO `mb_user` VALUES (10035, 'Macmillan', '1054197367@qq.com', '25f9e794323b453885f5181f1b624d0b', 'do not go gentle into the good night.', 'NORMAL', '2021-02-19 19:04:10', '2021-02-19 19:09:24', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/bd147a76ac364b3b93f36ce9a610888117.jpg');
INSERT INTO `mb_user` VALUES (10036, 'Price', '1054197368@qq.com', '25f9e794323b453885f5181f1b624d0b', '我是个刷子', 'NORMAL', '2021-02-20 01:39:08', '2021-02-20 13:55:02', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/cd5ef9d51379480faa828b497b52b33332125324-896B-4182-891C-07F9630D9CBA.gif');
INSERT INTO `mb_user` VALUES (10037, '无关风月', '317980448@qq.com', 'e89100336bec42fe9bed6ccf28176b24', '小小梦魇2好玩', 'NORMAL', '2021-02-20 12:40:35', '2021-02-20 19:14:40', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/1817a08ff03a485e9c6ab1da92821c1962506810-B8C6-49EA-B494-31EF7DE5601C.jpeg');
INSERT INTO `mb_user` VALUES (10038, '大帅哥', '1031371437@qq.com', 'e89100336bec42fe9bed6ccf28176b24', '我是小杨我喜欢睡觉', 'NORMAL', '2021-02-20 13:18:43', '2021-02-20 13:20:48', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/b383dc71d6a749d882a9b5dcabc56b7dimg-6a1a90d06d9b0f79a883b422a2539ee8.jpg');
INSERT INTO `mb_user` VALUES (10039, '用户_6030e84133af6637ef4c5592', '764250656@qq.com', '0f17ebb909784a1b8c96753d0b4f8a24', '写点什么吧', 'NORMAL', '2021-02-20 18:45:21', '2021-02-20 18:45:21', '1', 'http://qorvzwz4v.hn-bkt.clouddn.com/default.jpg');

-- ----------------------------
-- Table structure for mb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mb_user_role`;
CREATE TABLE `mb_user_role`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint(11) UNSIGNED NOT NULL COMMENT '关联的用户ID',
  `roleId` bigint(11) UNSIGNED NOT NULL COMMENT '关联的角色ID',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '状态，记录当前记录是否有效，1有效，0无效',
  `create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10053 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mb_user_role
-- ----------------------------
INSERT INTO `mb_user_role` VALUES (10044, 10035, 10000, '1', '2021-02-19 19:04:10', '2021-02-19 19:04:10');
INSERT INTO `mb_user_role` VALUES (10045, 10035, 10001, '1', '2021-02-19 19:06:04', '2021-02-19 19:06:04');
INSERT INTO `mb_user_role` VALUES (10046, 10036, 10000, '1', '2021-02-20 01:39:09', '2021-02-20 01:39:09');
INSERT INTO `mb_user_role` VALUES (10047, 10037, 10000, '1', '2021-02-20 12:40:36', '2021-02-20 12:40:36');
INSERT INTO `mb_user_role` VALUES (10048, 10037, 10001, '1', '2021-02-20 12:56:24', '2021-02-20 12:56:24');
INSERT INTO `mb_user_role` VALUES (10049, 10038, 10000, '1', '2021-02-20 13:18:43', '2021-02-20 13:18:43');
INSERT INTO `mb_user_role` VALUES (10050, 10038, 10001, '1', '2021-02-20 13:19:51', '2021-02-20 13:19:51');
INSERT INTO `mb_user_role` VALUES (10052, 10039, 10000, '1', '2021-02-20 18:45:21', '2021-02-20 18:45:21');
INSERT INTO `mb_user_role` VALUES (10053, 10039, 10001, '1', '2021-02-20 18:59:54', '2021-02-20 18:59:54');

SET FOREIGN_KEY_CHECKS = 1;
