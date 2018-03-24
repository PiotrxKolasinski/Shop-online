package com.onlineshop.controller;

import com.onlineshop.entity.Order;
import com.onlineshop.entity.Product;
import com.onlineshop.entity.Cart;
import com.onlineshop.service.order.OrderService;
import com.onlineshop.service.products.ProductService;
import com.onlineshop.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private ProductService productsService;

    private OrderService orderService;

    private CartService cartService;

    @Autowired
    public AdminController(ProductService productsService, OrderService orderService, CartService cartService) {
        this.productsService = productsService;
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @RequestMapping(value ={"/", ""})
    public String showHome(){
        return "home";
    }

    @RequestMapping(value = "/displayProducts")
    public String show(Model model){

        List<Product> products = new ArrayList<>();

        model.addAttribute("title", "Products");
        model.addAttribute("setOption", "Products");
        model.addAttribute("products", products);

        return "settings/displayProducts";
    }

    @RequestMapping(value = "/find", method= RequestMethod.GET)
    public String findProducts(Model model, @RequestParam(value = "select") String select, @RequestParam(value = "action", required = false) String action){

        if(action.equals("New")) return "redirect:/admin/new?select="+select;

        List<Product> products = productsService.getProducts(select);
        model.addAttribute("title", select);
        model.addAttribute("products", products);

        return "settings/displayProducts";
    }

    @RequestMapping(value ="/new")
    public String addProduct(@RequestParam(value = "select", required = false) String select, Model model) {

        model.addAttribute("product", new Product());

        model.addAttribute("title", select);
        model.addAttribute("category", select);

        return "settings/showProduct";
    }

    @RequestMapping(value ="/view", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") int id, @RequestParam("select") String select) {

        productsService.changeView(id);

        return "redirect:/admin/find?select="+select+"&action=FIND";
    }

    @RequestMapping(value ="/show")
    public String showProduct(@RequestParam("id") int id, Model model) {

        Product product = productsService.getProduct(id);

        model.addAttribute("title", product.getName());
        model.addAttribute("product", product);
        model.addAttribute("category", product.getCategory().split("/")[0]);
        model.addAttribute("setOption", "showOneProduct");

        return "settings/showProduct";
    }

    @RequestMapping(value ="/updateProduct")
    public String updateProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult,
                                @RequestParam(value = "id") int id, @RequestParam(value = "check") String category, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("title", category);
            model.addAttribute("category", category);
            return "settings/showProduct";
        }

        if(!product.getCategory().split("/")[0].equals(category))
            product.setCategory(category+"/"+product.getCategory());

        if(id!=0) {
            product.setId(id);
            product.setView("no");
        }

        productsService.saveProduct(product);

        return "redirect:/admin/displayProducts";
    }

    @RequestMapping(value = "/showOrders")
    public String showOrders(Model model){

        Set<Order> orders = orderService.getAllUnfinishedOrders();

        model.addAttribute("title", "Orders");
        model.addAttribute("orders", orders);

        return "settings/orders";
    }

    @RequestMapping(value="/details")
    public String showDetails(@RequestParam("id") int id, Model model){

        Order order = orderService.getOrder(id);

        model.addAttribute("title", "Settings");
        model.addAttribute("order", order);

        return "settings/details";
    }

    @RequestMapping(value="/paid")
    public String changePaid(@RequestParam("id") int id){

        orderService.setPaid(id);

        return "redirect:/admin/showOrders";
    }

    @RequestMapping(value="/done")
    public String changeOrderStatus(@RequestParam("id") int id){

        orderService.setStatus("done", id);

        return "redirect:/admin/showOrders";
    }
}
