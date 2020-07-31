package com.hutu.base;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 *
 * @author hutu
 * @date 2020/7/10 12:47 下午
 */
@Data
public class BaseEntity implements Serializable {

	/**
	 * 创建人
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	private Long createId;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_MS_PATTERN)
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_MS_PATTERN)
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 更新人
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "更新人")
	private Long updateId;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_MS_PATTERN)
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_MS_PATTERN)
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	/**
	 * 状态[0:未删除,1:删除]
	 */
	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;
}
