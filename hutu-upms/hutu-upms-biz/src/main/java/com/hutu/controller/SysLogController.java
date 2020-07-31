
package com.hutu.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.core.R;
import com.hutu.entity.SysLog;
import com.hutu.service.SysLogService;
import com.hutu.vo.SysLogVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  控制器
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/syslog")
@Api(value = "", tags = "接口")
public class SysLogController {

	private SysLogService sysLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入sysLog")
	public R<SysLog> detail(SysLog sysLog) {
		SysLog detail = sysLogService.getOne(new QueryWrapper<>(sysLog));
		return R.ok(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list/{current}/{pageSize}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入sysLog")
	public R<IPage<SysLog>> list(@ApiParam("当前页")@PathVariable("current")int current,
									 @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									 SysLog sysLog) {
		IPage<SysLog> pages = sysLogService.page(new Page<>(current,pageSize), new QueryWrapper<>(sysLog));
		return R.ok(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page/{current}/{pageSize}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入sysLog")
	public R<IPage<SysLogVO>> page(@ApiParam("当前页")@PathVariable("current")int current,
									   @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									   SysLogVO sysLog) {
		IPage<SysLogVO> pages = sysLogService.selectSysLogPage(new Page<>(current,pageSize), sysLog);
		return R.ok(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入sysLog")
	public R save(@Valid @RequestBody SysLog sysLog) {
		return sysLogService.save(sysLog)?R.ok():R.error();
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入sysLog")
	public R update(@Valid @RequestBody SysLog sysLog) {
		return sysLogService.updateById(sysLog)?R.ok():R.error();
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入sysLog")
	public R submit(@Valid @RequestBody SysLog sysLog) {
		return sysLogService.saveOrUpdate(sysLog)?R.ok():R.error();
	}

	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return sysLogService.removeByIds(StrUtil.split(ids, CharUtil.COMMA))?R.ok():R.error();
	}

}
