
package com.hutu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.entity.SysLog;
import com.hutu.vo.SysLogVO;

/**
 *  服务类
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface SysLogService extends IService<SysLog> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param sysLog
	 * @return
	 */
	IPage<SysLogVO> selectSysLogPage(IPage<SysLogVO> page, SysLogVO sysLog);

}
