
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.User;
import com.hutu.mapper.UserMapper;
import com.hutu.service.UserService;
import com.hutu.vo.UserVO;
import org.springframework.stereotype.Service;

/**
 * 系统用户表 服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Override
	public IPage<UserVO> selectUserPage(IPage<UserVO> page, UserVO user) {
		return page.setRecords(baseMapper.selectUserPage(page, user));
	}

}
