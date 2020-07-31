
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.UserOrganization;
import com.hutu.vo.UserOrganizationVO;

/**
 * 用户组织关联表 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserOrganizationService extends IService<UserOrganization> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userOrganization
	 * @return
	 */
	IPage<UserOrganizationVO> selectUserOrganizationPage(IPage<UserOrganizationVO> page, UserOrganizationVO userOrganization);

}
