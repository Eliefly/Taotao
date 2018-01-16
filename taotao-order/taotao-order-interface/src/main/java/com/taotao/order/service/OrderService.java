package com.taotao.order.service;

import com.taotao.pojo.Order;

/**
 * OrderService
 *
 * @author eliefly
 * @date 2018-01-15
 */
public interface OrderService {

    /**
     * 保存订单
     *
     * @param order 订单
     * @return 订单
     */
    String saveOrder(Order order);

    /**
     * 根据订单id查询订单
     *
     * @param orderId 订单id
     * @return 订单
     */
    Order queryOrderByOrderId(String orderId);
}
