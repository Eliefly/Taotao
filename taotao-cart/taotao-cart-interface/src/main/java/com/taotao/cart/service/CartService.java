package com.taotao.cart.service;

import com.taotao.pojo.Cart;

import java.util.List;

/**
 * CartService
 *
 * @author eliefly
 * @date 2018-01-14
 */
public interface CartService {
    /**
     * 将商品加入购物车中
     *
     * @param userId 用户id
     * @param itemId 商品id
     * @param num    商品数量
     */
    void saveItemIntoCart(Long userId, Long itemId, Integer num);

    /**
     * 根据用户id查询redis中购物车数据
     *
     * @param userId 用户id
     * @return 购物车列表
     */
    List<Cart> queryCartByUserId(Long userId);

    /**
     * 更新购物车中商品的数量
     *
     * @param userId 用户id
     * @param itemId 商品id
     * @param num    商品数量
     */
    void updateCartItemNum(Long userId, Long itemId, Integer num);

    /**
     * 删除购物车中的商品
     *
     * @param userId 用户
     * @param itemId 商品id
     */
    void deleteCartItem(Long userId, Long itemId);
}
