package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;


public interface DishService {

    //新增菜品的方法
    public void saveWithFlavor(DishDTO dishDTO);

    //菜品分页查询方法
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //批量删除菜品
    void deleteBatch(List<Long> ids);

    //根据id查询对应菜品和口味数据
    DishVO getByIdWithFlavor(Long id);

    //根据id修改菜品的基本信息和口味信息
    void updateWithFlavor(DishDTO dishDTO);
}
