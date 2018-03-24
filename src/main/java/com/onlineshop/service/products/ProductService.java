package com.onlineshop.service.products;

import com.onlineshop.entity.Product;

import java.util.List;


public interface ProductService {

     List<Product> getProducts(String category);

     List<Product> getProductsBetweenValue(int startCountFrom, int numberOfRecords, String category);

     Product getProduct(int id);

     int getSize(String category);

     List<Product> searchProducts(String search, int page);

     void saveProduct(Product product);

     void changeView(int id);

     boolean nameExists(String name);

}
