package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yutaoshao
 * @description: TODO
 * @date 2025/8/20 14:11
 */

@Mapper
public interface SetmealDishMapper {

    // select setmeal_id from setmeal_dish where dish_id in dishIds
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
