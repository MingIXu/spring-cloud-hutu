
package com.hutu.vo;

import com.hutu.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户表视图实体类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserVO对象", description = "系统用户表")
public class UserVO extends User {
	private static final long serialVersionUID = 1L;

}
