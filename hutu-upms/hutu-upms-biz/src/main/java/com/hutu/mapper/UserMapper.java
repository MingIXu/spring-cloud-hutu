
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.User;
import com.hutu.vo.UserVO;

import java.util.List;

/**
 * 系统用户表 Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	List<UserVO> selectUserPage(IPage page, UserVO user);

}
