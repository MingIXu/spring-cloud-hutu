
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.RolePermission;
import com.hutu.mapper.RolePermissionMapper;
import com.hutu.service.RolePermissionService;
import com.hutu.vo.RolePermissionVO;
import org.springframework.stereotype.Service;

/**
 * 角色权限关联表 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

	@Override
	public IPage<RolePermissionVO> selectRolePermissionPage(IPage<RolePermissionVO> page, RolePermissionVO rolePermission) {
		return page.setRecords(baseMapper.selectRolePermissionPage(page, rolePermission));
	}

}
