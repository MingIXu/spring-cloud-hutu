package com.hutu.cloud.es;

import com.hutu.cloud.es.constant.ElasticConstant;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

@Data
@Accessors(chain = true)
@Document(indexName = "book")
// @Setting(settingPath = "book_setting.json")
public class Book {

	@Id
	@Field(type = FieldType.Keyword)
	private String id;

	@MultiField(mainField = @Field(type = FieldType.Text, analyzer = ElasticConstant.PINYIN),
			otherFields = { @InnerField(suffix = ElasticConstant.KEYWORD, type = FieldType.Keyword) })
	private String name;

	@Field(type = FieldType.Text)
	private String summary;

	@Field(type = FieldType.Integer)
	private Integer price;

	@Field(type = FieldType.Text)
	private String age;

	@Field(type = FieldType.Integer)
	private List<Integer> nums;

}
