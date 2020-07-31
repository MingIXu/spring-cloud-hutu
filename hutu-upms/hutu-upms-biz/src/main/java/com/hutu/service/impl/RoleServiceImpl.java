
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.Role;
import com.hutu.mapper.RoleMapper;
import com.hutu.service.RoleService;
import com.hutu.vo.RoleVO;
import org.springframework.stereotype.Service;

/**
 * 角色 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Override
	public IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role) {
		return page.setRecords(baseMapper.selectRolePage(page, role));
	}

}
