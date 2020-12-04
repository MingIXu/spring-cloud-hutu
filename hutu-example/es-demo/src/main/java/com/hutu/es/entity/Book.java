package com.hutu.es.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Data
@Accessors(chain = true)
@Document(indexName = "book2")
public class Book {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_pinyin_analyzer",searchAnalyzer = "ik_pinyin_analyzer")
    private String name;

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String summary;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Text)
    private String age;

    @Field(type = FieldType.Integer)
    private List<Integer> nums;

}
