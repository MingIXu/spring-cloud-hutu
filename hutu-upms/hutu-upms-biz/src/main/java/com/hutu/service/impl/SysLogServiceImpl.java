
package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.entity.SysLog;
import com.hutu.mapper.SysLogMapper;
import com.hutu.service.SysLogService;
import com.hutu.vo.SysLogVO;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

	@Override
	public IPage<SysLogVO> selectSysLogPage(IPage<SysLogVO> page, SysLogVO sysLog) {
		return page.setRecords(baseMapper.selectSysLogPage(page, sysLog));
	}

}
