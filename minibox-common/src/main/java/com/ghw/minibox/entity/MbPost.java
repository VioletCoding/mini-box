package com.ghw.minibox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (MbPost)实体类
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 * <p>
 * 注解:
 * 1- @Document 标示映射到Elasticsearch文档上的领域对象 {
 * indexName:索引库名次，相当于MySQL中数据库的概念
 * type(已过时):对象类型，MySQL中表的概念，新版本只允许一个文档一种类型，所以不需要再指定了
 * shards:默认分片数，ES是支持分片存储的
 * replicas:副本数，ES是支持容灾的
 * }
 * 2- @Id 表示是文档的Id，文档可以认为是MySQL中表行的概念
 * 3- @Field{
 * FieldType:文档中字段的类型，不填的话自动识别
 * index:是否建立倒排索引，默认是true，这也是ES快的原因
 * store:是否进行存储
 * analyzer:分词器名次
 * }
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "mb_post", replicas = 0)
public class MbPost implements Serializable {
    private static final long serialVersionUID = -34132088850314000L;
    @Id
    @ApiModelProperty(notes = "主键")
    private Long id;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(notes = "关联的用户ID")
    private Long uid;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(notes = "记录这个帖子在哪个版块下发表的，默认是【杂谈】")
    private Long bid;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @ApiModelProperty(notes = "帖子标题")
    @NotEmpty(message = "帖子标题title不能为空")
    private String title;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @ApiModelProperty(notes = "帖子内容")
    @NotEmpty(message = "帖子内容content不能为空")
    private String content;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(notes = "帖子状态，0有效，1删除")
    private String postState;

    @Field(type = FieldType.Text)
    @ApiModelProperty(notes = "帖子封面图")
    @NotEmpty(message = "帖子封面图不能为空")
    private String coverImg;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(notes = "首页评论数,这个字段不存数据库，只是为了方便首页列表展示评论数")
    private Long countComment;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @Field(type = FieldType.Auto)
    @ApiModelProperty(notes = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(notes = "状态，记录该条状态是否有效,0有效，1无效")
    private Integer state;

    @Field(type = FieldType.Nested)
    @ApiModelProperty(notes = "一篇帖子多条评论")
    private List<MbComment> commentList;

    @Field(type = FieldType.Nested)
    @ApiModelProperty(notes = "一个帖子对应一个版块")
    private MbBlock mbBlock;

    @Field(type = FieldType.Nested)
    @ApiModelProperty(notes = "一个帖子对应一个用户，该用户是发帖人")
    private MbUser mbUser;
}