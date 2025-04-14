package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    //添加购物车方法
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    //查看购物车方法
    List<ShoppingCart> showShoppingCart();

    //删除购物车中一个商品
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

    //清空购物车
    void cleanShoppingCart();
}
