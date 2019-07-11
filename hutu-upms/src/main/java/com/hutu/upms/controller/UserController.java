package com.hutu.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.core.entity.R;
import com.hutu.common.core.validator.group.UpdateGroup;
import com.hutu.common.security.annotation.RequiresPermissions;
import com.hutu.upms.entity.User;
import com.hutu.upms.service.OrganizationService;
import com.hutu.upms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */

@Api(tags = "系统用户表")
@RestController
@RequestMapping("user")
public class UserController{

    @Autowired
    private UserService userService;

    @ApiOperation("获取page")
    @RequiresPermissions("user:page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current, @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("所在部门") @RequestParam(required = false) String departmentId, @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> page=new Page<>(current,pageSize);
        if (StringUtils.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        queryWrapper.eq(StringUtils.isNotEmpty(departmentId),"departmentId",departmentId);
        userService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象")@Validated User data){
        return userService.save(data)? R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public R delete(@ApiParam("数据对象id")@PathVariable("id")String id){
        return userService.removeById(id)?R.ok():R.error("删除错误");
    }
    @ApiOperation("更新")
    @PostMapping("/update")
    public R update(@RequestBody @ApiParam("数据对象")@Validated(UpdateGroup.class)User data){
        return userService.updateById(data)?R.ok():R.error("更新错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",userService.getById(id));
    }

    @Autowired
    OrganizationService organizationService;

    @ApiOperation("获取组织树")
    @GetMapping("/getOrgTree")
    public R getPermissionTree(){
        List treeData = organizationService.getPermissionTree();
        return R.ok().put("treeData",treeData);
    }
}
