package com.onlineshop.service.products;

import com.onlineshop.dao.products.ProductDAO;
import com.onlineshop.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class.getName());

    private final ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO computersDAO) {
        this.productDAO = computersDAO;
    }


    @Override
    @Transactional
    public List<Product> getProducts(String category) {
        return productDAO.getProducts(category);
    }

    @Override
    @Transactional
    public List<Product> getProductsBetweenValue(int startCountFrom, int numberOfRecords, String category) {
        return productDAO.getProductsBetweenValue(startCountFrom, numberOfRecords, category);
    }

    @Override
    @Transactional
    public Product getProduct(int id) {
        return productDAO.getProduct(id);
    }

    @Override
    @Transactional
    public int getSize(String category) {
        return productDAO.getSize(category);
    }

    @Override
    @Transactional
    public List<Product> searchProducts(String search, int page) {
        return productDAO.searchProducts(search, page);
    }

    @Override
    @Transactional
    public void saveProduct(Product product) {
        productDAO.saveProduct(product);
        logger.info("[ProductService] save product with id: " + product.getId());
    }

    @Override
    @Transactional
    public void updateView(int id) {
        Product existingProduct = productDAO.getProduct(id);

        if(existingProduct.getView().equals("no")) {
            existingProduct.setView("yes");
        } else {
            existingProduct.setView("no");
        }

        productDAO.saveProduct(existingProduct);
        logger.info("[ProductService] update product view with id: " + existingProduct.getId());
    }

    @Override
    @Transactional
    public boolean nameExists(String name) {
        return productDAO.nameExists(name);
    }
}
