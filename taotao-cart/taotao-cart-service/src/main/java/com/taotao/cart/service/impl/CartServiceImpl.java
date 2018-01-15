package com.taotao.cart.service.impl;

import com.taotao.cart.service.CartService;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.service.ItemService;
import com.taotao.pojo.Cart;
import com.taotao.pojo.Item;
import com.taotao.redis.impl.JedisClientCluster;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CartServiceImpl
 *
 * @author eliefly
 * @date 2018-01-14
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${TAOTAO_CART_KEY}")
    private String taotaoCartKey;

    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Autowired
    private ItemService itemService;

    @Override
    public void saveItemIntoCart(Long userId, Long itemId, Integer num) {

        // 用户已登录. 需要先把用户之前的购物车数据查询出来. 合并要增加的商品

        // 从redis中查询购物车数据。
        List<Cart> cartList = queryCartByUserId(userId);

        // 遍历购物车
        Cart cart = null;
        for (Cart c : cartList) {

            if (c.getItemId().longValue() == itemId.longValue()) {
                cart = c;
            }
        }

        // 1. 如果商品已在购物车存在, 则更新购物车中商品数量
        if (cart != null) {
            cart.setNum(cart.getNum() + num);
            cart.setUpdated(new Date());
        } else {
            // 2.如果商品已在购物车存在, 则更新购物车中商品数量
            Item item = itemService.queryById(itemId);

            cart = new Cart();
            cart.setUserId(userId);
            cart.setItemId(itemId);
            cart.setItemTitle(item.getTitle());
            cart.setItemImage(item.getImage());
            cart.setItemPrice(item.getPrice());
            cart.setNum(num);
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());

            cartList.add(cart);

        }

        // 把购物车数据保存到redis中
        jedisClientCluster.set(taotaoCartKey + userId, JsonUtils.objectToJson(cartList));

    }

    @Override
    public List<Cart> queryCartByUserId(Long userId) {
        // 从redis中查询用户购物车数据
        String json = jedisClientCluster.get(taotaoCartKey + userId);

        if (StringUtils.isNotBlank(json)) {
            return JsonUtils.jsonToList(json, Cart.class);
        }
        // 如果redis中购物车为空, 返回一集合
        return new ArrayList<>();
    }

    @Override
    public void updateCartItemNum(Long userId, Long itemId, Integer num) {
        // 从redis中查询购物车数据。
        List<Cart> cartList = queryCartByUserId(userId);

        // 遍历商品. 若商品存在则更新商品数量, 不存在则不做任何操作
        for (Cart cart : cartList) {
            if (cart.getItemId().longValue() == itemId.longValue()) {
                cart.setNum(num);
                cart.setUpdated(new Date());
                // 把购物车更新到redis中
                jedisClientCluster.set(taotaoCartKey + userId, JsonUtils.objectToJson(cartList));
                break;
            }

        }

    }

    @Override
    public void deleteCartItem(Long userId, Long itemId) {
        // 从redis中查询购物车数据。
        List<Cart> cartList = queryCartByUserId(userId);

        // 遍历商品. 若商品存在则删除商品, 不存在则不做任何操作
        for (Cart cart : cartList) {
            if (cart.getItemId().longValue() == itemId.longValue()) {
                cartList.remove(cart);
                // 把购物车更新到redis中
                jedisClientCluster.set(taotaoCartKey + userId, JsonUtils.objectToJson(cartList));
                break;
            }

        }

    }
}
