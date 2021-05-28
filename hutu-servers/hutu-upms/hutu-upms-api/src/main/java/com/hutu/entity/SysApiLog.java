
package com.hutu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author ehealth-generator
 * @since 2020-06-19
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysLog对象", description = "SysLog对象")
public class SysApiLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId(value = "id")
    private Integer id;
    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    private String description;
    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String type;
    /**
     * 操作用户
     */
    @ApiModelProperty(value = "操作用户")
    private String username;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @TableField("startTime")
    private LocalDateTime startTime;
    /**
     * 消耗时间
     */
    @ApiModelProperty(value = "消耗时间")
    @TableField("spendTime")
    private Long spendTime;
    /**
     * 根路径
     */
    @ApiModelProperty(value = "根路径")
    @TableField("basePath")
    private String basePath;
    /**
     * URI
     */
    @ApiModelProperty(value = "URI")
    private String uri;
    /**
     * 请求类型
     */
    @ApiModelProperty(value = "请求类型")
    @TableField("className")
    private String className;
    /**
     * 请求类型
     */
    @ApiModelProperty(value = "请求类型")
    private String method;
    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String parameter;
    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识")
    @TableField("userAgent")
    private String userAgent;
    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址")
    private String ip;
    /**
     * 请求结果
     */
    @ApiModelProperty(value = "请求结果")
    private String result;
    /**
     * 权限值
     */
    @ApiModelProperty(value = "权限值")
    private String permissions;


}
