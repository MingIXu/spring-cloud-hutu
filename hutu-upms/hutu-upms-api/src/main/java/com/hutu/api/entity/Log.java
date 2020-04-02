package com.hutu.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 操作描述
     */
	private String description;
    /**
     * 操作用户
     */
	private String username;
    /**
     * 操作时间
     */
	private Date startTime;
    /**
     * 消耗时间
     */
	private Long spendTime;
    /**
     * 根路径
     */
	private String basePath;
    /**
     * URI
     */
	private String uri;
    /**
     * URL
     */
	private String url;
    /**
     * 请求类型
     */
	private String method;
    /**
     * 请求参数
     */
	private String parameter;
    /**
     * 用户标识
     */
	private String userAgent;
    /**
     * IP地址
     */
	private String ip;
    /**
     * 请求结果
     */
	private String result;
    /**
     * 权限值
     */
	private String permissions;


}
