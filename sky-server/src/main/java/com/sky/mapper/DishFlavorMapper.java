package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yutaoshao
 * @description: TODO
 * @date 2025/8/19 18:45
 */

@Mapper

public interface DishFlavorMapper {


    void insertBatch(List<DishFlavor> flavors);
}
