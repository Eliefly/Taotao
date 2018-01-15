package com.taotao.portal.controller;

import com.taotao.cart.service.CartService;
import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.Cart;
import com.taotao.pojo.User;
import com.taotao.portal.service.CartCookieService;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * CartController
 *
 * @author eliefly
 * @date 2018-01-15
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Value("${TT_TICKET_COOKIE_NAME}")
    private String taotaoTicketCookieName;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartCookieService cartCookieService;

    /**
     * 将商品加入购物车中
     *
     * @param itemId  商品id
     * @param num     商品数量
     * @param request http请求
     * @return 购物车展示
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String saveItemIntoCart(@PathVariable(value = "itemId") Long itemId,
                                   Integer num,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        // 获取用户登录Cookie信息
        String ticket = CookieUtils.getCookieValue(request, taotaoTicketCookieName);
        // 使用Coookie中的ticket查询用户是否已登录
        User user = userService.queryUserByTicker(ticket);

        if (user != null) {
            // 用户已登录
            cartService.saveItemIntoCart(user.getId(), itemId, num);
        } else {
            // 用户未登录
            cartCookieService.addItemIntoCookieCart(itemId, num, request, response);
        }

        return "redirect:/cart/show";

    }

    /**
     * 显示商品也详情
     *
     * @param model   数据模型
     * @param request http请求
     * @return 跳转购物车页面
     */
    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String showCart(Model model, HttpServletRequest request) {

        // 获取用户登录Cookie信息
        String ticket = CookieUtils.getCookieValue(request, taotaoTicketCookieName);
        // 使用Coookie中的ticket查询用户是否已登录
        User user = userService.queryUserByTicker(ticket);

        List<Cart> cartList = null;

        if (user != null) {
            cartList = cartService.queryCartByUserId(user.getId());
        } else {
            cartList = cartCookieService.queryCookieCart(request);
        }

        model.addAttribute("cartList", cartList);
        return "cart";

    }

    /**
     * 更新购物车中商品的数量
     *
     * @param itemId  商品
     * @param num     商品数量
     * @param request http请求
     */
    @RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
    @ResponseBody
    public void updateCartItemNum(@PathVariable("itemId") Long itemId,
                                  @PathVariable("num") Integer num,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        String ticket = CookieUtils.getCookieValue(request, taotaoTicketCookieName);

        User user = userService.queryUserByTicker(ticket);

        if (user != null) {
            cartService.updateCartItemNum(user.getId(), itemId, num);

        } else {
            cartCookieService.updateCookieCartItemNum(itemId, num, request, response);
        }

    }

    /**
     * 删除购物车中的商品
     *
     * @param itemId  商品id
     * @param request http请求
     * @return 跳转购物车页面
     */
    @RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
    public String deleteCartItem(@PathVariable("itemId") Long itemId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        String ticket = CookieUtils.getCookieValue(request, taotaoTicketCookieName);

        User user = userService.queryUserByTicker(ticket);

        if (user != null) {
            // 用户已登录
            cartService.deleteCartItem(user.getId(), itemId);
        } else {
            cartCookieService.deleteCookieCartItem(itemId, request, response);
        }

        return "redirect:/cart/show";

    }

}
