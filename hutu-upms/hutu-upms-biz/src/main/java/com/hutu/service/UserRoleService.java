
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.UserRole;
import com.hutu.vo.UserRoleVO;

/**
 * 用户角色关联表 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserRoleService extends IService<UserRole> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userRole
	 * @return
	 */
	IPage<UserRoleVO> selectUserRolePage(IPage<UserRoleVO> page, UserRoleVO userRole);

}
