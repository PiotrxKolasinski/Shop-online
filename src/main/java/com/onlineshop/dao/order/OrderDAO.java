package com.onlineshop.dao.order;

import com.onlineshop.entity.Order;
import com.onlineshop.entity.Cart;

import java.util.List;
import java.util.Set;

public interface OrderDAO {

    void saveOrder(Order order);

    Set<Order> getAllUnfinishedOrders();

    Order getOrder(int id);
}
