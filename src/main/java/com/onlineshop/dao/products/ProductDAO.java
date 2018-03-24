package com.onlineshop.dao.products;

import com.onlineshop.entity.Product;

import java.util.List;

public interface ProductDAO {

    List<Product> getProducts(String category);

    List<Product> getProductsBetweenValue(int startCountFrom, int numberOfRecords, String category);

    Product getProduct(int id);

    int getSize(String category);

    List<Product> searchProducts(String search, int page);

    void saveProduct(Product product);

    boolean nameExists(String name);
}
