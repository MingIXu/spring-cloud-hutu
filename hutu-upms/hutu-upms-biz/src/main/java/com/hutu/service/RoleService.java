
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.Role;
import com.hutu.vo.RoleVO;

/**
 * 角色 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface RoleService extends IService<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

}
