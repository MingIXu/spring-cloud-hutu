
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.Tenant;
import com.hutu.vo.TenantVO;

/**
 * 组织 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface TenantService extends IService<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	IPage<TenantVO> selectTenantPage(IPage<TenantVO> page, TenantVO tenant);

}
