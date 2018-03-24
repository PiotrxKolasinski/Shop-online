package com.onlineshop.dao.cart;

import com.onlineshop.entity.Customer;
import com.onlineshop.entity.Cart;

import java.util.List;

public interface CartDAO {

    List<Cart> getCartList(Customer customer);

    void deleteProductFromCart(int id);

    void updateCart(Cart customerCart);

    List<Cart> findCartByOrderId(int orderId);

    List<Cart> findCartByCustomerIdAndStatus(int customerId);
}
