
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.UserOrganization;
import com.hutu.vo.UserOrganizationVO;

import java.util.List;

/**
 * 用户组织关联表 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserOrganizationMapper extends BaseMapper<UserOrganization> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userOrganization
	 * @return
	 */
	List<UserOrganizationVO> selectUserOrganizationPage(IPage page, UserOrganizationVO userOrganization);

}
