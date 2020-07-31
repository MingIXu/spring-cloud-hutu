
package com.hutu.vo;

import com.hutu.entity.UserPermission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户权限关联表视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserPermissionVO对象", description = "用户权限关联表")
public class UserPermissionVO extends UserPermission {
	private static final long serialVersionUID = 1L;

}
