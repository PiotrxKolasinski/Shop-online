package com.onlineshop.service.cart;

import com.onlineshop.dao.customer.CustomerDAO;
import com.onlineshop.dao.cart.CartDAO;
import com.onlineshop.dao.products.ProductDAO;
import com.onlineshop.entity.Customer;
import com.onlineshop.entity.Cart;
import com.onlineshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CustomerDAO customerDAO;

    private ProductDAO productDAO;

    private CartDAO shopCartDAO;

    @Autowired
    public CartServiceImpl(CustomerDAO customerDAO, ProductDAO productDAO, CartDAO cartDAO) {
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
        this.shopCartDAO = cartDAO;
    }

    @Override
    @Transactional
    public void addProductToCart(int quantity, int product_id) {

        Customer customer = customerDAO.getCurrentCustomer();

        List<Cart> cart = shopCartDAO.getCartList(customer);

        boolean isProductInShopCart = false;

        int shopCartNumber = 0;

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId() == product_id) {
                isProductInShopCart = true;
                shopCartNumber = i;
                cart.get(i).setQuantity(cart.get(i).getQuantity() + quantity);
                break;
            }
        }

        if (!isProductInShopCart) {
            Cart customerCart = new Cart(quantity, "Product in cart");

            Product product = productDAO.getProduct(product_id);

            customerCart.setProduct(product);

            customer.checkCart();

            customer.getProductsInCart().add(customerCart);

            customerCart.setCustomer(customer);

            shopCartDAO.updateCart(customerCart);
        } else {
            shopCartDAO.updateCart(customer.getProductsInCart().get(shopCartNumber));
        }

    }

    @Override
    @Transactional
    public List<Cart> getCartList() {

        Customer customer = customerDAO.getCurrentCustomer();

        return shopCartDAO.getCartList(customer);
    }

    @Override
    @Transactional
    public void deleteProductFromCart(int id) {
        shopCartDAO.deleteProductFromCart(id);
    }

    @Override
    @Transactional
    public void updateCart(Cart customerCart) {
       shopCartDAO.updateCart(customerCart);
    }

    @Override
    @Transactional
    public List<Cart> findCartByOrderId(int orderId) {
        return shopCartDAO.findCartByOrderId(orderId);
    }

    @Override
    @Transactional
    public List<Cart> findCartByCustomerIdAndStatus(int customerId) {
        return shopCartDAO.findCartByCustomerIdAndStatus(customerId);
    }
}
