package com.onlineshop.controller;

import com.onlineshop.entity.Address;
import com.onlineshop.entity.Customer;
import com.onlineshop.entity.Order;
import com.onlineshop.entity.Cart;
import com.onlineshop.format.PasswordFormat;
import com.onlineshop.service.customer.CustomerService;
import com.onlineshop.service.order.OrderService;
import com.onlineshop.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public UserController(CustomerService customerService, OrderService orderService, CartService cartService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @RequestMapping(value = {"/", ""})
    public String showHome() {
        return "home";
    }

    @RequestMapping(value = "/general")
    public String showAccount(Model model) {
        Customer customer = customerService.getCurrentCustomer();
        model.addAttribute("customer", customer);
        model.addAttribute("passwordFormat", new PasswordFormat());
        return "account/general";
    }

    @RequestMapping(value = "/yourOrders")
    public String showYourOrders(Model model) {
        List<Cart> carts = cartService.findCartByCustomerIdAndStatus(customerService.getCurrentCustomer().getId());
        Set<Order> orders = new LinkedHashSet<>();

        for (Cart temp : carts) {
            orders.add(temp.getOrder());
        }

        model.addAttribute("orders", orders);
        return "account/yourOrders";
    }

    @RequestMapping(value = "/address")
    public String showAddress(Model model) {
        Customer customer = customerService.getCurrentCustomer();
        model.addAttribute("customer", customer);
        return "account/address";
    }

    @RequestMapping(value = "/updateAddress")
    public String updateAddress(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "account/address";
        }

        Customer currentCustomer = customerService.getCurrentCustomer();

        if(currentCustomer.getAddress()==null){
            Address address = new Address();
            currentCustomer.setAddress(address);
        }

        currentCustomer.getAddress().setStreetName(customer.getAddress().getStreetName());
        currentCustomer.getAddress().setStreetNumber(customer.getAddress().getStreetNumber());
        currentCustomer.getAddress().setCity(customer.getAddress().getCity());
        currentCustomer.getAddress().setZipCode(customer.getAddress().getZipCode());
        currentCustomer.getAddress().setFirstname(customer.getAddress().getFirstname());
        currentCustomer.getAddress().setLastname(customer.getAddress().getLastname());
        customerService.saveCustomer(currentCustomer);
        return "redirect:/user/address";
    }

    @RequestMapping(value = "/changePassword")
    public String changePassword(@ModelAttribute("passwordFormat") @Valid PasswordFormat passwordFormat, BindingResult bindingResult, Model model) {
        Customer customer = customerService.getCurrentCustomer();

        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customer);
            return "account/general";
        }

        boolean confirmPass = passwordFormat.getPassword().equals(passwordFormat.getConfirmPassword());

        if (!confirmPass) {
            return "redirect:/user/general?pass";
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(passwordFormat.getPassword());
        encodedPassword = "{bcrypt}" + encodedPassword;

        customer.setPassword(encodedPassword);
        customerService.saveCustomer(customer);
        return "redirect:/user/general?success";
    }

    @RequestMapping(value = "/details")
    public String showDetails(@RequestParam("id") int id, Model model) {
        Order order = orderService.getOrder(id);
        model.addAttribute("title", "Settings");
        model.addAttribute("order", order);
        return "account/details";
    }
}
