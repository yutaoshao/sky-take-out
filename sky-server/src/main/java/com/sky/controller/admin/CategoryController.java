package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yutaoshao
 * @description: TODO
 * @date 2025/7/21 17:41
 */

@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询：{}", categoryPageQueryDTO);
        return Result.success(categoryService.pageQuery(categoryPageQueryDTO));
    }


    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }


    @PutMapping
    @ApiOperation("更新分类")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("更新分类：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }


    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用分类")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用/禁用分类：{}", status, id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type) {
        return Result.success(categoryService.list(type));
    }



}
