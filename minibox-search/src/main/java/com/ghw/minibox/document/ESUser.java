package com.ghw.minibox.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author Violet
 * @description ES文档对象，用户
 * 注解：
 * Document标示映射到Elasticsearch文档上的领域对象
 * -索引库名次，mysql中数据库的概念
 * -String indexName();
 * -文档类型，mysql中表的概念
 * -String type() default "";
 * -默认分片数
 * -short shards() default 5;
 * -默认副本数量
 * -short replicas() default 1;
 * Id表示是文档的id，文档可以认为是mysql中表行的概念
 * Field文档中字段的类型
 * 不需要中文分词的字段设置成@Field(type = FieldType.Keyword)类型，
 * 需要中文分词的设置成@Field(analyzer = "ik_max_word",type = FieldType.Text)类型。
 * @date 2020/11/19
 */
@Document(indexName = "mbUser", replicas = 0)
@Data
public class ESUser implements Serializable {

    private static final long serialVersionUID = -1L;
    @Id
    private Long uid;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String nickname;
    @Field(type = FieldType.Keyword)
    private String username;
    @Field(type = FieldType.Auto)
    private String description;
    @Field(type = FieldType.Keyword)
    private String level;
    @Field(type = FieldType.Auto)
    private Double walletBalance;
    @Field(type = FieldType.Keyword)
    private String userState;
    @Field(type = FieldType.Integer)
    private Integer exp;
    @Field(type = FieldType.Integer)
    private Integer state;

}
