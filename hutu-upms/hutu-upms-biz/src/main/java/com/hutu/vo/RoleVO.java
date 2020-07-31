
package com.hutu.vo;

import com.hutu.entity.Role;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RoleVO对象", description = "角色")
public class RoleVO extends Role {
	private static final long serialVersionUID = 1L;

}
