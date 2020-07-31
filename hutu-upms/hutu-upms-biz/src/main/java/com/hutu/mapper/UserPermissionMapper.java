
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.UserPermission;
import com.hutu.vo.UserPermissionVO;

import java.util.List;

/**
 * 用户权限关联表 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserPermissionMapper extends BaseMapper<UserPermission> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userPermission
	 * @return
	 */
	List<UserPermissionVO> selectUserPermissionPage(IPage page, UserPermissionVO userPermission);

}
