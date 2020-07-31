
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.RolePermission;
import com.hutu.vo.RolePermissionVO;

import java.util.List;

/**
 * 角色权限关联表 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param rolePermission
	 * @return
	 */
	List<RolePermissionVO> selectRolePermissionPage(IPage page, RolePermissionVO rolePermission);

}
