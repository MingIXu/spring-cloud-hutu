
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.RolePermission;
import com.hutu.vo.RolePermissionVO;

/**
 * 角色权限关联表 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface RolePermissionService extends IService<RolePermission> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rolePermission
	 * @return
	 */
	IPage<RolePermissionVO> selectRolePermissionPage(IPage<RolePermissionVO> page, RolePermissionVO rolePermission);

}
