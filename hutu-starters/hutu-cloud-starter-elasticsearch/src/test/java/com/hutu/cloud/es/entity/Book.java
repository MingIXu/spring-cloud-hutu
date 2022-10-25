package com.hutu.cloud.es.entity;

import com.hutu.cloud.es.constant.SearchConstant;
import lombok.Data;
import lombok.experimental.Accessors;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

@Data
@Accessors(chain = true)
@Document(indexName = "book2")
@Setting(replicas = 0)
public class Book {

    @Id
    private Integer id;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = SearchConstant.PINYIN),
            otherFields = {@InnerField(suffix = SearchConstant.KEYWORD, type = FieldType.Keyword)})
    private String name;

    @Field(type = FieldType.Text)
    private String summary;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Keyword)
    private String age;

    @Field(type = FieldType.Integer)
    private List<Integer> nums;
}