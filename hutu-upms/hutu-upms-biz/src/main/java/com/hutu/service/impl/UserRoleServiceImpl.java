
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.UserRole;
import com.hutu.mapper.UserRoleMapper;
import com.hutu.service.UserRoleService;
import com.hutu.vo.UserRoleVO;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联表 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	@Override
	public IPage<UserRoleVO> selectUserRolePage(IPage<UserRoleVO> page, UserRoleVO userRole) {
		return page.setRecords(baseMapper.selectUserRolePage(page, userRole));
	}

}
