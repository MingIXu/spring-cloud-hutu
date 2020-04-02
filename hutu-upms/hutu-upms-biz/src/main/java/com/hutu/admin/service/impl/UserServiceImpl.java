package com.hutu.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.admin.mapper.UserMapper;
import com.hutu.admin.service.UserService;
import com.hutu.api.entity.User;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
