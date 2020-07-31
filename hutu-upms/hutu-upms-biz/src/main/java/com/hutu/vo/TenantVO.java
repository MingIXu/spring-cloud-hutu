
package com.hutu.vo;

import com.hutu.entity.Tenant;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TenantVO对象", description = "组织")
public class TenantVO extends Tenant {
	private static final long serialVersionUID = 1L;

}
