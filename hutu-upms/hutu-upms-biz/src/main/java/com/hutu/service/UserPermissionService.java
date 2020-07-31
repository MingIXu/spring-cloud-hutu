
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.UserPermission;
import com.hutu.vo.UserPermissionVO;

/**
 * 用户权限关联表 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserPermissionService extends IService<UserPermission> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userPermission
	 * @return
	 */
	IPage<UserPermissionVO> selectUserPermissionPage(IPage<UserPermissionVO> page, UserPermissionVO userPermission);

}
