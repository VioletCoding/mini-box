package com.ghw.minibox.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Violet
 * @description
 * @date 2021/1/26
 * <p>
 * * <p>
 * * 注解:
 * * 1- @Document 标示映射到Elasticsearch文档上的领域对象 {
 * * indexName:索引库名次，相当于MySQL中数据库的概念
 * * type(已过时):对象类型，MySQL中表的概念，新版本只允许一个文档一种类型，所以不需要再指定了
 * * shards:默认分片数，ES是支持分片存储的
 * * replicas:副本数，ES是支持容灾的
 * * }
 * * 2- @Id 表示是文档的Id，文档可以认为是MySQL中表行的概念
 * * 3- @Field{
 * * FieldType:文档中字段的类型，不填的话自动识别
 * * index:是否建立倒排索引，默认是true，这也是ES快的原因
 * * store:是否进行存储
 * * analyzer:分词器名次
 * * }
 */
@Data
@Document(indexName = "post_model")
public class ESPostModel implements Serializable {
    private static final long serialVersionUID = -3413208885031214000L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("关联的用户ID")
    private Long authorId;

    @ApiModelProperty("记录这个帖子在哪个版块下发表的，默认是「杂谈」")
    private Long blockId;

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty("帖子内容")
    private String content;

    @ApiModelProperty("帖子封面图")
    private String photoLink;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @ApiModelProperty("状态，记录该条状态是否有效,1有效，0无效")
    private String state;
}
