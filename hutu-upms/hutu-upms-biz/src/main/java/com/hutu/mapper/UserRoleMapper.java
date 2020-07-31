
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.UserRole;
import com.hutu.vo.UserRoleVO;

import java.util.List;

/**
 * 用户角色关联表 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userRole
	 * @return
	 */
	List<UserRoleVO> selectUserRolePage(IPage page, UserRoleVO userRole);

}
