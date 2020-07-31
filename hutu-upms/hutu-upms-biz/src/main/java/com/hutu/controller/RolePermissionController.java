
package com.hutu.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.core.R;
import com.hutu.entity.RolePermission;
import com.hutu.service.RolePermissionService;
import com.hutu.vo.RolePermissionVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色权限关联表 控制器
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/rolepermission")
@Api(value = "角色权限关联表", tags = "角色权限关联表接口")
public class RolePermissionController {

	private RolePermissionService rolePermissionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入rolePermission")
	public R<RolePermission> detail(RolePermission rolePermission) {
		RolePermission detail = rolePermissionService.getOne(new QueryWrapper<>(rolePermission));
		return R.ok(detail);
	}

	/**
	 * 分页 角色权限关联表
	 */
	@GetMapping("/list/{current}/{pageSize}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入rolePermission")
	public R<IPage<RolePermission>> list(@ApiParam("当前页")@PathVariable("current")int current,
									 @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									 RolePermission rolePermission) {
		IPage<RolePermission> pages = rolePermissionService.page(new Page<>(current,pageSize), new QueryWrapper<>(rolePermission));
		return R.ok(pages);
	}

	/**
	 * 自定义分页 角色权限关联表
	 */
	@GetMapping("/page/{current}/{pageSize}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入rolePermission")
	public R<IPage<RolePermissionVO>> page(@ApiParam("当前页")@PathVariable("current")int current,
									   @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									   RolePermissionVO rolePermission) {
		IPage<RolePermissionVO> pages = rolePermissionService.selectRolePermissionPage(new Page<>(current,pageSize), rolePermission);
		return R.ok(pages);
	}

	/**
	 * 新增 角色权限关联表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入rolePermission")
	public R save(@Valid @RequestBody RolePermission rolePermission) {
		return rolePermissionService.save(rolePermission)?R.ok():R.error();
	}

	/**
	 * 修改 角色权限关联表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入rolePermission")
	public R update(@Valid @RequestBody RolePermission rolePermission) {
		return rolePermissionService.updateById(rolePermission)?R.ok():R.error();
	}

	/**
	 * 新增或修改 角色权限关联表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入rolePermission")
	public R submit(@Valid @RequestBody RolePermission rolePermission) {
		return rolePermissionService.saveOrUpdate(rolePermission)?R.ok():R.error();
	}

	/**
	 * 删除 角色权限关联表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return rolePermissionService.removeByIds(StrUtil.split(ids, CharUtil.COMMA))?R.ok():R.error();
	}

}
