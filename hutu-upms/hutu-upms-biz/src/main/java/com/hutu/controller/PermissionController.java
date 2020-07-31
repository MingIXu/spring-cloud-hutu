
package com.hutu.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.core.R;
import com.hutu.entity.Permission;
import com.hutu.service.PermissionService;
import com.hutu.vo.PermissionVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 权限 控制器
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/permission")
@Api(value = "权限", tags = "权限接口")
public class PermissionController {

	private PermissionService permissionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入permission")
	public R<Permission> detail(Permission permission) {
		Permission detail = permissionService.getOne(new QueryWrapper<>(permission));
		return R.ok(detail);
	}

	/**
	 * 分页 权限
	 */
	@GetMapping("/list/{current}/{pageSize}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入permission")
	public R<IPage<Permission>> list(@ApiParam("当前页")@PathVariable("current")int current,
									 @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									 Permission permission) {
		IPage<Permission> pages = permissionService.page(new Page<>(current,pageSize), new QueryWrapper<>(permission));
		return R.ok(pages);
	}

	/**
	 * 自定义分页 权限
	 */
	@GetMapping("/page/{current}/{pageSize}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入permission")
	public R<IPage<PermissionVO>> page(@ApiParam("当前页")@PathVariable("current")int current,
									   @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									   PermissionVO permission) {
		IPage<PermissionVO> pages = permissionService.selectPermissionPage(new Page<>(current,pageSize), permission);
		return R.ok(pages);
	}

	/**
	 * 新增 权限
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入permission")
	public R save(@Valid @RequestBody Permission permission) {
		return permissionService.save(permission)?R.ok():R.error();
	}

	/**
	 * 修改 权限
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入permission")
	public R update(@Valid @RequestBody Permission permission) {
		return permissionService.updateById(permission)?R.ok():R.error();
	}

	/**
	 * 新增或修改 权限
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入permission")
	public R submit(@Valid @RequestBody Permission permission) {
		return permissionService.saveOrUpdate(permission)?R.ok():R.error();
	}

	/**
	 * 删除 权限
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return permissionService.removeByIds(StrUtil.split(ids, CharUtil.COMMA))?R.ok():R.error();
	}

}
