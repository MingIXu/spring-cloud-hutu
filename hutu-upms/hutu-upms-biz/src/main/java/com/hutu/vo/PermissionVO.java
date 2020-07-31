
package com.hutu.vo;

import com.hutu.entity.Permission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PermissionVO对象", description = "权限")
public class PermissionVO extends Permission {
	private static final long serialVersionUID = 1L;

}
