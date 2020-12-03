package com.alibaba.csp.sentinel.dashboard.repository.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc sentinel 索引实体类，索引初始胡请使用 {@link com.alibaba.csp.sentinel.RefreshIndexTest#initIndex()}
 *
 * @author hutu
 * @date 2020/12/3 10:04 上午
 */
@Data
@Accessors(chain = true)
@Document(indexName = "sentinel")
public class ESMetric implements Serializable {
    private static final long serialVersionUID = 7200023615444172716L;

    /**id，主键*/
    @Id
    private String id;

    /**创建时间*/
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss") // 指定存储格式
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")// 数据格式转换，并加上8小时进行存储
    private Date gmtCreate;

    /**修改时间*/
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss") // 指定存储格式
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")// 数据格式转换，并加上8小时进行存储
    private Date gmtModified;

    /**应用名称*/
    @Field(type = FieldType.Text)
    private String app;

    /**统计时间*/
    @Field(type = FieldType.Long)
    private Long timestamp;

    /**资源名称*/
    @Field(type = FieldType.Text)
    private String resource;

    /**通过qps*/
    @Field(type = FieldType.Long)
    private Long passQps;

    /**成功qps*/
    @Field(type = FieldType.Long)
    private Long successQps;

    /**限流qps*/
    @Field(type = FieldType.Long)
    private Long blockQps;

    /**发送异常的次数*/
    @Field(type = FieldType.Long)
    private Long exceptionQps;

    /**所有successQps的rt的和*/
    @Field(type = FieldType.Double)
    private Double rt;

    /**本次聚合的总条数*/
    @Field(type = FieldType.Integer)
    private Integer count;

    /**资源的hashCode*/
    @Field(type = FieldType.Integer)
    private Integer resourceCode;

}
