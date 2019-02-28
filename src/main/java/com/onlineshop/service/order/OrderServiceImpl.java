package com.onlineshop.service.order;

import com.onlineshop.dao.order.OrderDAO;
import com.onlineshop.entity.Cart;
import com.onlineshop.entity.Order;
import com.onlineshop.service.cart.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class.getName());

    private final OrderDAO orderDAO;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, CartService cartService) {
        this.orderDAO = orderDAO;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderDAO.saveOrder(order);
        logger.info("[OrderService] save order with order id: " + order.getOrderId());
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
    public void updateStatus(String status, int id) {
        List<Cart> carts = cartService.findCartByOrderId(id);

        for(Cart temp : carts){
            temp.setStatus(status);
            cartService.updateCart(temp);
        }
        logger.info("[OrderService] update carts status with order id: " + id);
    }

    @Override
    @Transactional
    public void togglePaid(int id) {
        Order existingOrder = orderDAO.getOrder(id);

        if(existingOrder.getPaid().equals("no")) existingOrder.setPaid("yes");
        else existingOrder.setPaid("no");

        orderDAO.saveOrder(existingOrder);
        logger.info("[OrderService] toggle order paid with id: " + existingOrder.getOrderId());
    }
}
