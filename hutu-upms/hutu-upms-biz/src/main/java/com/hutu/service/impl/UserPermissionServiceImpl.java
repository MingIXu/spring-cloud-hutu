
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.UserPermission;
import com.hutu.mapper.UserPermissionMapper;
import com.hutu.service.UserPermissionService;
import com.hutu.vo.UserPermissionVO;
import org.springframework.stereotype.Service;

/**
 * 用户权限关联表 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements UserPermissionService {

	@Override
	public IPage<UserPermissionVO> selectUserPermissionPage(IPage<UserPermissionVO> page, UserPermissionVO userPermission) {
		return page.setRecords(baseMapper.selectUserPermissionPage(page, userPermission));
	}

}
