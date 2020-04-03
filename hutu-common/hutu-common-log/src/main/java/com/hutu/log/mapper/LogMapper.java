package com.hutu.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.log.entity.LogApi;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志
 * @author hutu
 * @date 2019-12-11 16:35
 */
@Mapper
public interface LogMapper extends BaseMapper<LogApi> {

}
