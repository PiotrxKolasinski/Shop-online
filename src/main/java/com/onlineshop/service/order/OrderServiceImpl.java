package com.onlineshop.service.order;

import com.onlineshop.dao.order.OrderDAO;
import com.onlineshop.entity.Order;
import com.onlineshop.entity.Cart;
import com.onlineshop.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    private CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, CartService cartService) {
        this.orderDAO = orderDAO;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderDAO.saveOrder(order);
    }

    @Override
    @Transactional
    public Set<Order> getAllUnfinishedOrders() {
        return orderDAO.getAllUnfinishedOrders();
    }

    @Override
    @Transactional
    public Order getOrder(int id) {
        return orderDAO.getOrder(id);
    }

    @Override
    @Transactional
    public void setStatus(String status, int id) {

        Order order = orderDAO.getOrder(id);

        List<Cart> carts = cartService.findCartByOrderId(id);

        for(Cart temp : carts){
            temp.setStatus(status);
            cartService.updateCart(temp);
        }

    }

    @Override
    @Transactional
    public void setPaid(int id) {

        Order order = orderDAO.getOrder(id);

        if(order.getPaid().equals("no")) order.setPaid("yes");
        else order.setPaid("no");

        orderDAO.saveOrder(order);
    }
}
