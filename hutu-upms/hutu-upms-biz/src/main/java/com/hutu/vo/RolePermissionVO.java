
package com.hutu.vo;

import com.hutu.entity.RolePermission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限关联表视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RolePermissionVO对象", description = "角色权限关联表")
public class RolePermissionVO extends RolePermission {
	private static final long serialVersionUID = 1L;

}
