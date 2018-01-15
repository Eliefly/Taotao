package com.taotao.portal.service;

import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.service.ItemService;
import com.taotao.pojo.Cart;
import com.taotao.pojo.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CartCookieService
 *
 * @author eliefly
 * @date 2018-01-15
 */
@Service
public class CartCookieService {

    @Value("${TT_CART_COOKIE_NAME}")
    private String taotaoCartCookieName;

    @Autowired
    private ItemService itemService;

    /**
     * 把商品添加到cookie中的购物车
     *
     * @param itemId   商品名称
     * @param num      商品数量
     * @param request  http请求
     * @param response http响应
     */
    public void addItemIntoCookieCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 获取原有的cookie购物车数据
        List<Cart> cartList = queryCookieCart(request);

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
            cart.setId(null);
            cart.setUserId(null);
            cart.setItemId(itemId);
            cart.setItemTitle(item.getTitle());

            // 设置商品图片url
            if (item.getImages() != null) {
                cart.setItemImage(item.getImages()[0]);
            } else {
                cart.setItemImage(null);
            }

            cart.setItemPrice(item.getPrice());
            cart.setNum(num);
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());

            cartList.add(cart);
        }

        CookieUtils.setCookie(request, response, taotaoCartCookieName, JsonUtils.objectToJson(cartList),
                60 * 60 * 24, true);

    }

    /**
     * 查询cookie中的购物车数据
     *
     * @param request http请求
     * @return cookie购物车数据
     */
    public List<Cart> queryCookieCart(HttpServletRequest request) {

        String json = CookieUtils.getCookieValue(request, taotaoCartCookieName);

        if (StringUtils.isNotBlank(json)) {
            try {
                String decodeStr = URLDecoder.decode(json, "utf-8");
                return JsonUtils.jsonToList(decodeStr, Cart.class);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>();
    }

    /**
     * 更新cookie购物车中商品的数量
     *
     * @param itemId   商品id
     * @param num      商品数目
     * @param request  http请求
     * @param response http响应
     */
    public void updateCookieCartItemNum(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 获取原有的cookie购物车数据
        List<Cart> cartList = queryCookieCart(request);

        // 遍历商品. 若商品存在则更新商品数量, 不存在则不做任何操作
        for (Cart cart : cartList) {
            if (cart.getItemId().longValue() == itemId.longValue()) {
                cart.setNum(num);
                cart.setUpdated(new Date());
                // 把购物车更新到cookie中
                CookieUtils.setCookie(request, response, taotaoCartCookieName, JsonUtils.objectToJson(cartList),
                        60 * 60 * 24, true);
                break;
            }

        }
    }

    /**
     * 删除cookie购物车中的商品
     *
     * @param itemId   商品id
     * @param request  http请求
     * @param response http响应
     */
    public void deleteCookieCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {

        // 获取原有的cookie购物车数据
        List<Cart> cartList = queryCookieCart(request);
        // 遍历商品. 若商品存在则删除商品, 不存在则不做任何操作
        for (Cart cart : cartList) {
            if (cart.getItemId().longValue() == itemId.longValue()) {
                cartList.remove(cart);
                // 把购物车更新到redis中
                // 把购物车更新到cookie中
                CookieUtils.setCookie(request, response, taotaoCartCookieName, JsonUtils.objectToJson(cartList),
                        60 * 60 * 24, true);
                break;
            }

        }
    }
}
