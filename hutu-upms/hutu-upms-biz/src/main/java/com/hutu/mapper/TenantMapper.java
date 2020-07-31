
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.Tenant;
import com.hutu.vo.TenantVO;

import java.util.List;

/**
 * 组织 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface TenantMapper extends BaseMapper<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	List<TenantVO> selectTenantPage(IPage page, TenantVO tenant);

}
