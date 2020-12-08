package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (MbReply)实体类
 *
 * @author Violet
 * @since 2020-12-08 16:33:03
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbReply implements Serializable {
    private static final long serialVersionUID = -57131563410726990L;
    /**
     * 回复ID
     */
    @ApiModelProperty(notes = "回复ID")
    private Long rid;
    /**
     * 回复类型，在哪里回复，TR表示在文章（帖子）下回复他们的评论
     */
    @ApiModelProperty(notes = "回复类型，在哪里回复，TR表示在文章（帖子）下回复他们的评论")
    private String type;
    /**
     * 回复谁，填写UID
     */
    @ApiModelProperty(notes = "回复谁，填写UID")
    private Long replyWho;
    /**
     * 回复内容
     */
    @ApiModelProperty(notes = "回复内容")
    private String replyContent;
    /**
     * 回复时间
     */
    @ApiModelProperty(notes = "回复时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replyDate;
    /**
     * 谁发表的回复
     */
    @ApiModelProperty(notes = "谁发表的回复")
    private Long replyUid;
    /**
     * 在哪篇文章（帖子）下的回复
     */
    @ApiModelProperty(notes = "在哪篇文章（帖子）下的回复")
    private Long replyInPost;

    @ApiModelProperty(notes = "在哪个游戏下的回复")
    private Long replyInGame;

    @ApiModelProperty(notes = "记录状态，0有效，1无效，默认0")
    private Integer status;
    /**
     * 回复了哪一条评论
     */
    @ApiModelProperty(notes = "回复了哪一条评论")
    private Long replyInComment;

    @ApiModelProperty(notes = "回复人的信息")
    private MbUser mbUser;


}