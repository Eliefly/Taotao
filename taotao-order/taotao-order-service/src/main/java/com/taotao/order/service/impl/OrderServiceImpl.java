package com.taotao.order.service.impl;

import com.taotao.mapper.OrderItemMapper;
import com.taotao.mapper.OrderMapper;
import com.taotao.mapper.OrderShippingMapper;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.Order;
import com.taotao.pojo.OrderItem;
import com.taotao.pojo.OrderShipping;
import com.taotao.redis.impl.JedisClientCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * OrderServiceImpl
 *
 * @author eliefly
 * @date 2018-01-15
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_TAOTAO_ORDERID_INCR}")
    private String orderTaotaoOrderIdIncr;

    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    @Override
    public String saveOrder(Order order) {
        // 创建订单号, 用户id加唯一数
        String orderId = "" + order.getUserId() + jedisClientCluster.incr(orderTaotaoOrderIdIncr);

        // 保存订单数据
        order.setOrderId(orderId);
        order.setStatus(1);

        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());

        orderMapper.insert(order);

        // 保存订单项数据
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderId);
            orderItemMapper.insert(orderItem);
        }

        // 保存订单物流信息
        OrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(order.getCreateTime());
        orderShipping.setUpdated(orderShipping.getCreated());

        orderShippingMapper.insert(orderShipping);

        return orderId;
    }

    @Override
    public Order queryOrderByOrderId(String orderId) {

        // 查询订单数据
        Order order = orderMapper.selectByPrimaryKey(orderId);

        // 查询订单项数据, 设置到订单中
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        List<OrderItem> items = orderItemMapper.select(orderItem);
        order.setOrderItems(items);

        // 查询顶大物流信息, 设置到订单中
        OrderShipping orderShipping = orderShippingMapper.selectByPrimaryKey(orderId);
        order.setOrderShipping(orderShipping);

        return order;
    }
}
