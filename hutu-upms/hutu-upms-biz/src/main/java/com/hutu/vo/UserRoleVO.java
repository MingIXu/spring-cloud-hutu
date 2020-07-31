
package com.hutu.vo;

import com.hutu.entity.UserRole;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联表视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserRoleVO对象", description = "用户角色关联表")
public class UserRoleVO extends UserRole {
	private static final long serialVersionUID = 1L;

}
