
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.User;
import com.hutu.vo.UserVO;

/**
 * 系统用户表 服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserService extends IService<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<UserVO> selectUserPage(IPage<UserVO> page, UserVO user);

}
