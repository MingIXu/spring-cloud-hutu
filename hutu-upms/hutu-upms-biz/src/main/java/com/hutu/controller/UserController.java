
package com.hutu.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.core.R;
import com.hutu.entity.User;
import com.hutu.service.UserService;
import com.hutu.vo.UserVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 系统用户表 控制器
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "系统用户表", tags = "系统用户表接口")
public class UserController {

	private UserService userService;

	/**
	 * 详情
	 */
	@PostMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入user")
	public R<User> detail(User user) {
		User detail = userService.getOne(new QueryWrapper<>(user));
		return R.ok(detail);
	}

	/**
	 * 分页 系统用户表
	 */
	@GetMapping("/list/{current}/{pageSize}")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入user")
	public R<IPage<User>> list(@ApiParam("当前页")@PathVariable("current")int current,
							   @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
							   User user) {
		IPage<User> pages = userService.page(new Page<>(current,pageSize), new QueryWrapper<>(user));
		return R.ok(pages);
	}

	/**
	 * 自定义分页 系统用户表
	 */
	@GetMapping("/page/{current}/{pageSize}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入user")
	public R<IPage<UserVO>> page(@ApiParam("当前页")@PathVariable("current")int current,
								 @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
								 UserVO user) {
		IPage<UserVO> pages = userService.selectUserPage(new Page<>(current,pageSize), user);
		return R.ok(pages);
	}

	/**
	 * 新增 系统用户表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入user")
	public R save(@Valid @RequestBody User user) {
		return userService.save(user)?R.ok():R.error();
	}

	/**
	 * 修改 系统用户表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入user")
	public R update(@Valid @RequestBody User user) {
		return userService.updateById(user)?R.ok():R.error();
	}

	/**
	 * 新增或修改 系统用户表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入user")
	public R submit(@Valid @RequestBody User user) {
		return userService.saveOrUpdate(user)?R.ok():R.error();
	}


	/**
	 * 删除 系统用户表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return userService.removeByIds(StrUtil.split(ids, CharUtil.COMMA))?R.ok():R.error();
	}


}
