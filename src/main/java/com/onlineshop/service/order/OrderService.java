package com.onlineshop.service.order;

import com.onlineshop.entity.Order;
import com.onlineshop.entity.Cart;

import java.util.List;
import java.util.Set;

public interface OrderService {

    void saveOrder(Order order);

    Set<Order> getAllUnfinishedOrders();

    Order getOrder(int id);

    void setStatus(String status, int id);

    void setPaid(int id);
}
