
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.Permission;
import com.hutu.vo.PermissionVO;

/**
 * 权限 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface PermissionService extends IService<Permission> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param permission
	 * @return
	 */
	IPage<PermissionVO> selectPermissionPage(IPage<PermissionVO> page, PermissionVO permission);

}
