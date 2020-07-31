
package com.hutu.controller;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.core.R;
import com.hutu.entity.Role;
import com.hutu.service.RoleService;
import com.hutu.vo.RoleVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色 控制器
 *
 * @author hutu-generator
 * @since 2020-06-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色接口")
public class RoleController {

    private RoleService roleService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入role")
    public R<Role> detail(Role role) {
        Role detail = roleService.getOne(new QueryWrapper<>(role));
        return R.ok(detail);
    }

    /**
     * 分页 角色
     */
    @GetMapping("/list/{current}/{pageSize}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入role")
    public R<IPage<Role>> list(@ApiParam("当前页") @PathVariable("current") int current,
                               @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                               Role role) {
        IPage<Role> pages = roleService.page(new Page<>(current, pageSize), new QueryWrapper<>(role));
        return R.ok(pages);
    }

    /**
     * 自定义分页 角色
     */
    @GetMapping("/page/{current}/{pageSize}")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入role")
    public R<IPage<RoleVO>> page(@ApiParam("当前页") @PathVariable("current") int current,
                                 @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                                 RoleVO role) {
        IPage<RoleVO> pages = roleService.selectRolePage(new Page<>(current, pageSize), role);
        return R.ok(pages);
    }

    /**
     * 新增 角色
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入role")
    public R save(@Valid @RequestBody Role role) {
        return roleService.save(role) ? R.ok() : R.error();
    }

    /**
     * 修改 角色
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入role")
    public R update(@Valid @RequestBody Role role) {
        return roleService.updateById(role) ? R.ok() : R.error();
    }

    /**
     * 新增或修改 角色
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入role")
    public R submit(@Valid @RequestBody Role role) {
        return roleService.saveOrUpdate(role) ? R.ok() : R.error();
    }

    /**
     * 删除 角色
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return roleService.removeByIds(StrUtil.split(ids, CharUtil.COMMA)) ? R.ok() : R.error();
    }

}
