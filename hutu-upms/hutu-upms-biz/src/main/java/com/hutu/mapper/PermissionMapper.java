
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.Permission;
import com.hutu.vo.PermissionVO;

import java.util.List;

/**
 * 权限 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface PermissionMapper extends BaseMapper<Permission> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param permission
	 * @return
	 */
	List<PermissionVO> selectPermissionPage(IPage page, PermissionVO permission);

}
