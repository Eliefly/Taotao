package com.taotao.portal.controller;

import com.taotao.cart.service.CartService;
import com.taotao.common.util.CookieUtils;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.Cart;
import com.taotao.pojo.Order;
import com.taotao.pojo.User;
import com.taotao.sso.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderController
 *
 * @author eliefly
 * @date 2018-01-15
 */
@Controller
@RequestMapping(value = "/service/order")
public class OrderController {

    @Value("${TT_TICKET_COOKIE_NAME}")
    private String taotaoTicketCookieName;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    /**
     * 跳转订单结算页面
     *
     * @param request http请求
     * @param model   数据模型
     * @return 跳转页面
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createOrder(HttpServletRequest request, Model model) {

        /**
         * 此处逻辑实现较简单, 直接把登录用户购物车中商品全部拿去结算页面.
         * 实际应该只把购物车中选中的商品拿去结算.
         */

        // 拦截器已查询用户是否登录, 并把user放入request中了
        User user = (User) request.getAttribute("user");

        if (user != null) {
            // 根据用户查询购物车
            List<Cart> cartList = cartService.queryCartByUserId(user.getId());

            model.addAttribute("carts", cartList);

        }

        return "order-cart";

    }

    /**
     * 创建订单
     *
     * @param order
     * @param request http请求
     * @return 响应数据
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveOrder(Order order, HttpServletRequest request) {

        // 给订单设置用户信息
        String ticket = CookieUtils.getCookieValue(request, taotaoTicketCookieName);
        User user = userService.queryUserByTicker(ticket);

        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());

        String orderId = orderService.saveOrder(order);

        HashMap<String, Object> map = new HashMap<>(1);

        map.put("status", 200);
        map.put("data", orderId);

        return map;
    }

    /**
     * 跳转到订单成功页面
     *
     * @param orderId 订单id
     * @param model   数据模型
     * @return 订单成功页面
     */
    @RequestMapping(value = "success", method = RequestMethod.GET)
    public String success(@RequestParam("id") String orderId, Model model) {

        // 根据订单id查询订单
        Order order = orderService.queryOrderByOrderId(orderId);

        // 获取当前时间的两天后，即时送达时间
        String date = new DateTime().plusDays(2).toString("yyyy年MM月dd日HH时mm分ss秒SSS毫秒");

        model.addAttribute("order", order);
        model.addAttribute("date", date);

        return "success";

    }

}
