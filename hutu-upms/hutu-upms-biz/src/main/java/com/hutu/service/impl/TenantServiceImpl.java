
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.Tenant;
import com.hutu.mapper.TenantMapper;
import com.hutu.service.TenantService;
import com.hutu.vo.TenantVO;
import org.springframework.stereotype.Service;

/**
 * 组织 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

	@Override
	public IPage<TenantVO> selectTenantPage(IPage<TenantVO> page, TenantVO tenant) {
		return page.setRecords(baseMapper.selectTenantPage(page, tenant));
	}

}
