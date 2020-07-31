
package com.hutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hutu.entity.SysLog;
import com.hutu.vo.SysLogVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param sysLog
	 * @return
	 */
	List<SysLogVO> selectSysLogPage(IPage page, SysLogVO sysLog);

}
