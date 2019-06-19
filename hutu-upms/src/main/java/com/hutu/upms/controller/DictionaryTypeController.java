package com.hutu.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.core.entity.R;
import com.hutu.common.core.validator.group.UpdateGroup;
import com.hutu.upms.entity.DictionaryType;
import com.hutu.upms.service.DictionaryTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典类别表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */

@Api(tags = "字典类别表")
@RestController
@RequestMapping("dictionaryType")
public class DictionaryTypeController{
    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current, @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
        Page<DictionaryType> page=new Page<>(current,pageSize);
        if (StringUtils.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        dictionaryTypeService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象")@Validated DictionaryType data){
        return dictionaryTypeService.save(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @GetMapping("/delete/{id}")
    public R delete(@ApiParam("数据对象id")@PathVariable("id")String id){
        return dictionaryTypeService.removeById(id)?R.ok():R.error("删除错误");
    }
    @ApiOperation("更新")
    @PostMapping("/update")
    public R update(@RequestBody @ApiParam("数据对象")@Validated(UpdateGroup.class)DictionaryType data){
        return dictionaryTypeService.updateById(data)?R.ok():R.error("更新错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",dictionaryTypeService.getById(id));
    }

}
