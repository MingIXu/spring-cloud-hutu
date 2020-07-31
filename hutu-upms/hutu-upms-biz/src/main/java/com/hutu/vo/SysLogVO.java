
package com.hutu.vo;

import com.hutu.entity.SysLog;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysLogVO对象", description = "SysLogVO对象")
public class SysLogVO extends SysLog {
	private static final long serialVersionUID = 1L;

}
