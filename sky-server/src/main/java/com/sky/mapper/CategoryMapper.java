package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author yutaoshao
 * @description: TODO
 * @date 2025/7/21 18:33
 */

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (name, sort, type, create_time, update_time, create_user, update_user, status) " +
            "values (#{name}, #{sort}, #{type}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{status})")
    void insert(Category category);


    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    void update(Category category);

    @Select("select * from category where type = #{type}")
    List<Category> list(Integer type);
}
