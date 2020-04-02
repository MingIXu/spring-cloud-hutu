package com.hutu.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.admin.mapper.LogMapper;
import com.hutu.admin.service.LogService;
import com.hutu.api.entity.Log;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
