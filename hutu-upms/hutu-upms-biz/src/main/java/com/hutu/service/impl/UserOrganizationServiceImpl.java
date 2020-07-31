
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.UserOrganization;
import com.hutu.mapper.UserOrganizationMapper;
import com.hutu.service.UserOrganizationService;
import com.hutu.vo.UserOrganizationVO;
import org.springframework.stereotype.Service;

/**
 * 用户组织关联表 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class UserOrganizationServiceImpl extends ServiceImpl<UserOrganizationMapper, UserOrganization> implements UserOrganizationService {

	@Override
	public IPage<UserOrganizationVO> selectUserOrganizationPage(IPage<UserOrganizationVO> page, UserOrganizationVO userOrganization) {
		return page.setRecords(baseMapper.selectUserOrganizationPage(page, userOrganization));
	}

}
