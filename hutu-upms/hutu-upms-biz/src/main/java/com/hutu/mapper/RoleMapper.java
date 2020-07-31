
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.Role;
import com.hutu.vo.RoleVO;

import java.util.List;

/**
 * 角色 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	List<RoleVO> selectRolePage(IPage page, RoleVO role);

}
