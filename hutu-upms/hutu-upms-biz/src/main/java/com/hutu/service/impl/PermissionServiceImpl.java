
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.Permission;
import com.hutu.mapper.PermissionMapper;
import com.hutu.service.PermissionService;
import com.hutu.vo.PermissionVO;
import org.springframework.stereotype.Service;

/**
 * 权限 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Override
	public IPage<PermissionVO> selectPermissionPage(IPage<PermissionVO> page, PermissionVO permission) {
		return page.setRecords(baseMapper.selectPermissionPage(page, permission));
	}

}
