
package com.hutu.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.core.R;
import com.hutu.entity.Tenant;
import com.hutu.service.TenantService;
import com.hutu.vo.TenantVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 组织 控制器
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "组织", tags = "组织接口")
public class TenantController {

	private TenantService tenantService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tenant")
	public R<Tenant> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(new QueryWrapper<>(tenant));
		return R.ok(detail);
	}

	/**
	 * 分页 组织
	 */
	@GetMapping("/list/{current}/{pageSize}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tenant")
	public R<IPage<Tenant>> list(@ApiParam("当前页")@PathVariable("current")int current,
									 @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									 Tenant tenant) {
		IPage<Tenant> pages = tenantService.page(new Page<>(current,pageSize), new QueryWrapper<>(tenant));
		return R.ok(pages);
	}

	/**
	 * 自定义分页 组织
	 */
	@GetMapping("/page/{current}/{pageSize}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入tenant")
	public R<IPage<TenantVO>> page(@ApiParam("当前页")@PathVariable("current")int current,
									   @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									   TenantVO tenant) {
		IPage<TenantVO> pages = tenantService.selectTenantPage(new Page<>(current,pageSize), tenant);
		return R.ok(pages);
	}

	/**
	 * 新增 组织
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入tenant")
	public R save(@Valid @RequestBody Tenant tenant) {
		return tenantService.save(tenant)?R.ok():R.error();
	}

	/**
	 * 修改 组织
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入tenant")
	public R update(@Valid @RequestBody Tenant tenant) {
		return tenantService.updateById(tenant)?R.ok():R.error();
	}

	/**
	 * 新增或修改 组织
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入tenant")
	public R submit(@Valid @RequestBody Tenant tenant) {
		return tenantService.saveOrUpdate(tenant)?R.ok():R.error();
	}

	/**
	 * 删除 组织
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return tenantService.removeByIds(StrUtil.split(ids, CharUtil.COMMA))?R.ok():R.error();
	}

}
