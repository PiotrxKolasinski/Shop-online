package com.onlineshop.controller;

import com.onlineshop.entity.DeliveryAddress;
import com.onlineshop.entity.Customer;
import com.onlineshop.entity.Order;
import com.onlineshop.entity.Cart;
import com.onlineshop.service.customer.CustomerService;
import com.onlineshop.service.order.OrderService;
import com.onlineshop.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "shop")
public class CartController {
    private final CustomerService customerService;
    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public CartController(CustomerService customerService, CartService cartService, OrderService orderService) {
        this.customerService = customerService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/cart")
    public String showCart(Model model) {
        List<Cart> carts = cartService.getCartList();
        float Price = calculatePrice(carts);

        if (carts.size() == 0) {
            model.addAttribute("select", "null");
        } else {
            model.addAttribute("carts", carts);
            model.addAttribute("Price", Price);
            model.addAttribute("select", "notNull");
        }

        return "cart/cart";
    }

    @RequestMapping(value = "/addProductToCart")
    public String addProductToShopCart(@RequestParam("productId") int id, @RequestParam(value = "quantity") int quantity) {
        if (quantity == 1) {
            cartService.addProductToCart(1, id);
        } else {
            cartService.addProductToCart(quantity, id);
        }

        return "redirect:/shop/cart";
    }

    @RequestMapping(value = "/deleteProductFromCart")
    public String deleteProductFromShopCart(@RequestParam("id") int id) {
        cartService.deleteProductFromCart(id);
        return "redirect:/shop/cart";
    }

    @RequestMapping(value = "/detailsBeforeOrder")
    public String showDetailsBeforeOrder(@RequestParam("quantity") int[] quantity, Model model) {
        List<Cart> carts = cartService.getCartList();

        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getQuantity() != quantity[i]) {
                carts.get(i).setQuantity(quantity[i]);
                cartService.updateCart(carts.get(i));
            }
        }

        Customer customer = customerService.getCurrentCustomer();

        return prepareModelAttributeAndGetPageDetailsBeforeOrder(model, carts, customer);
    }

    @RequestMapping(value = "/order")
    public String showOrder(@ModelAttribute("customer") @Valid Customer customer,
                            BindingResult bindingResult,
                            @RequestParam("message") String message,
                            @RequestParam("delivery") String delivery,
                            Model model
    ) {
        if (bindingResult.hasErrors()) {
            List<Cart> carts = cartService.getCartList();
            return prepareModelAttributeAndGetPageDetailsBeforeOrder(model, carts, customer);
        }

            List<Cart> carts = cartService.getCartList();
            Customer currentCustomer = customerService.getCurrentCustomer();

            LocalDateTime orderDate = LocalDateTime.now();
            float deliveryPrice = Float.parseFloat(delivery.split("-")[1].trim());
            float price = calculatePrice(carts)+deliveryPrice;

            DeliveryAddress deliveryAddress = new DeliveryAddress(
                    customer.getAddress().getFirstname(),
                    customer.getAddress().getLastname(),
                    customer.getAddress().getStreetName(),
                    customer.getAddress().getStreetNumber(),
                    customer.getAddress().getZipCode(),
                    customer.getAddress().getCity()
            );

            Order order = new Order(
                    currentCustomer.getUsername(),
                    deliveryAddress,
                    price,
                    orderDate,
                    message,
                    "no",
                    delivery
            );

            order.setOrderedProducts(carts);
            orderService.saveOrder(order);

            for (Cart temp : carts) {
                temp.setStatus("ordered");
                temp.setOrder(order);
                cartService.updateCart(temp);
            }

        model.addAttribute("select", "afterOrder");
        return "cart/detailsBeforeOrder";
    }

    private String prepareModelAttributeAndGetPageDetailsBeforeOrder(Model model, List<Cart> carts, Customer customer) {
        LocalDate orderDate = LocalDate.now();
        float price = calculatePrice(carts);
        model.addAttribute("carts", carts);
        model.addAttribute("price", price);
        model.addAttribute("customer", customer);
        model.addAttribute("select", "beforeOrder");
        model.addAttribute("orderDate", orderDate);
        return "cart/detailsBeforeOrder";
    }

    private float calculatePrice(List<Cart> carts) {
        float price = 0;
        for (Cart cart : carts) {
            price = price + (cart.getQuantity() * cart.getProduct().getPrice());
        }
        return price;
    }
}
