package com.hutu.upms.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.core.entity.R;
import com.hutu.upms.admin.entity.Dictionary;
import com.hutu.upms.admin.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */

@Api(tags = "字典表")
@RestController
@RequestMapping("dictionary")
public class DictionaryController{
    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current, @ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord, @ApiParam("字典类型") @RequestParam(required = false) String type) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        Page<Dictionary> page=new Page<>(current,pageSize);
        if (StringUtils.isNotEmpty(keyWord)) {
            queryWrapper.like("valueCn", keyWord);
        }
        queryWrapper.eq(StringUtils.isNotEmpty(type),"typeKey",type);
        dictionaryService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Dictionary data){
        return dictionaryService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @DeleteMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id")@PathVariable("ids")String ids){
        return dictionaryService.removeByIds(Arrays.asList(ids))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",dictionaryService.getById(id));
    }
}
