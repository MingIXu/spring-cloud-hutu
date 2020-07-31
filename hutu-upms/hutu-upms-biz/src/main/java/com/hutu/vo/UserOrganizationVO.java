
package com.hutu.vo;

import com.hutu.entity.UserOrganization;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组织关联表视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserOrganizationVO对象", description = "用户组织关联表")
public class UserOrganizationVO extends UserOrganization {
	private static final long serialVersionUID = 1L;

}
