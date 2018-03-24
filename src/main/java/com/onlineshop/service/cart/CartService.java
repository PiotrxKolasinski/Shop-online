package com.onlineshop.service.cart;

import com.onlineshop.entity.Cart;

import java.util.List;

public interface CartService {

    void addProductToCart(int quantity, int product_id);

    List<Cart> getCartList();

    void deleteProductFromCart(int id);

    void updateCart(Cart customerCart);

    List<Cart> findCartByOrderId(int orderId);

    List<Cart> findCartByCustomerIdAndStatus(int customerId);

}
