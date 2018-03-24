package com.onlineshop.controller;

import com.onlineshop.entity.Product;
import com.onlineshop.service.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final int NUMBER_OF_PRODUCTS_ON_PAGE = 8;

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{product}")
    public String showProduct(@PathVariable("product") String category, @RequestParam("page") int page, Model model) {

        List<Product> products = productService.getProductsBetweenValue(
                page * NUMBER_OF_PRODUCTS_ON_PAGE - NUMBER_OF_PRODUCTS_ON_PAGE, NUMBER_OF_PRODUCTS_ON_PAGE, category);

        int numberOfPages = (productService.getSize(category) - 1) / NUMBER_OF_PRODUCTS_ON_PAGE + 1;

        List<String> pages = setPagesToView(numberOfPages, page);

        model.addAttribute("products", products);
        model.addAttribute("shortDescription", setShortDescriptionToArray(products));
        model.addAttribute("title", "Category: " + category);
        model.addAttribute("title2", category);
        model.addAttribute("numberOfPages", pages);

        return "product/products";
    }

    @RequestMapping(value = "/details")
    public String showDetails(@RequestParam("productId") int id, Model model) {
        
        Product product = productService.getProduct(id);

        model.addAttribute("product", product);
        model.addAttribute("shortDescription", product.getShortDescription().split("\n"));
        model.addAttribute("title", "Product details");
        
        return "product/details";
    }

    @RequestMapping(value = "/search")
    public String search(@RequestParam("page") int page, @RequestParam("searchResult") String searchResult, Model model) {

        searchResult = searchResult.trim();

        List<Product> products = productService.searchProducts(searchResult, page);

        List<Product> sortList = new ArrayList<>();
        int i = page * NUMBER_OF_PRODUCTS_ON_PAGE - NUMBER_OF_PRODUCTS_ON_PAGE;
        while (i < page * NUMBER_OF_PRODUCTS_ON_PAGE && i < products.size()) {
            sortList.add(products.get(i));
            i++;
        }

        int numberOfPages = (products.size() - 1) / NUMBER_OF_PRODUCTS_ON_PAGE + 1;

        List<String> pages = setPagesToView(numberOfPages, page);

        model.addAttribute("products", sortList);
        model.addAttribute("shortDescription", setShortDescriptionToArray(products));
        model.addAttribute("title", "Search");
        model.addAttribute("title2", "search");
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("numberOfPages", pages);

        return "product/products";
    }

    public List<String[]> setShortDescriptionToArray(List<Product> products) {
        List<String[]> shortDescription = new ArrayList<>();

        for (Product in : products) {
            String[] split = in.getShortDescription().split("\n");
            shortDescription.add(split);
        }

        return shortDescription;
    }


    public List<String> setPagesToView(int lastPage, int page) {

        List<String> pages = new ArrayList<>();

        if (lastPage <= 5) {
            for (int i = 1; i <= lastPage; i++) {
                pages.add(Integer.toString(i));
            }
            return pages;
        }

        pages.add("1");

        if (page <= 3) {
            pages.add("2");
            pages.add("3");
            pages.add("4");
            pages.add("...");
            pages.add(Integer.toString(lastPage));
            return pages;
        }

        if (page + 2 >= lastPage) {
            pages.add("...");
            pages.add(Integer.toString(lastPage - 3));
            pages.add(Integer.toString(lastPage - 2));
            pages.add(Integer.toString(lastPage - 1));
            pages.add(Integer.toString(lastPage));
            return pages;
        }

        pages.add("...");
        pages.add(Integer.toString(page - 1));
        pages.add(Integer.toString(page));
        pages.add(Integer.toString(page + 1));
        pages.add("...");
        pages.add(Integer.toString(lastPage));

        return pages;
    }
}
