
package com.hutu.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.core.R;
import com.hutu.entity.UserPermission;
import com.hutu.service.UserPermissionService;
import com.hutu.vo.UserPermissionVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户权限关联表 控制器
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userpermission")
@Api(value = "用户权限关联表", tags = "用户权限关联表接口")
public class UserPermissionController {

	private UserPermissionService userPermissionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userPermission")
	public R<UserPermission> detail(UserPermission userPermission) {
		UserPermission detail = userPermissionService.getOne(new QueryWrapper<>(userPermission));
		return R.ok(detail);
	}

	/**
	 * 分页 用户权限关联表
	 */
	@GetMapping("/list/{current}/{pageSize}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userPermission")
	public R<IPage<UserPermission>> list(@ApiParam("当前页")@PathVariable("current")int current,
									 @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									 UserPermission userPermission) {
		IPage<UserPermission> pages = userPermissionService.page(new Page<>(current,pageSize), new QueryWrapper<>(userPermission));
		return R.ok(pages);
	}

	/**
	 * 自定义分页 用户权限关联表
	 */
	@GetMapping("/page/{current}/{pageSize}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userPermission")
	public R<IPage<UserPermissionVO>> page(@ApiParam("当前页")@PathVariable("current")int current,
									   @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
									   UserPermissionVO userPermission) {
		IPage<UserPermissionVO> pages = userPermissionService.selectUserPermissionPage(new Page<>(current,pageSize), userPermission);
		return R.ok(pages);
	}

	/**
	 * 新增 用户权限关联表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userPermission")
	public R save(@Valid @RequestBody UserPermission userPermission) {
		return userPermissionService.save(userPermission)?R.ok():R.error();
	}

	/**
	 * 修改 用户权限关联表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userPermission")
	public R update(@Valid @RequestBody UserPermission userPermission) {
		return userPermissionService.updateById(userPermission)?R.ok():R.error();
	}

	/**
	 * 新增或修改 用户权限关联表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userPermission")
	public R submit(@Valid @RequestBody UserPermission userPermission) {
		return userPermissionService.saveOrUpdate(userPermission)?R.ok():R.error();
	}

	/**
	 * 删除 用户权限关联表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return userPermissionService.removeByIds(StrUtil.split(ids, CharUtil.COMMA))?R.ok():R.error();
	}

}
