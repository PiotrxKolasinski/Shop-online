package com.onlineshop.dao.products;

import com.onlineshop.entity.Product;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ProductDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> getProducts(String category) {
        String sql = "from Product where category like :category";
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery(sql, Product.class);
        query.setParameter("category", "%" + category + "%");
        return query.getResultList();
    }

    @Override
    public List<Product> getProductsBetweenValue(int startCountFrom, int numberOfRecords, String category) {
        String sql = "from Product where category like :category and view=:view";
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery(sql, Product.class);
        query.setParameter("category", "%" + category + "%");
        query.setParameter("view", "yes");
        query.setFirstResult(startCountFrom);
        query.setMaxResults(numberOfRecords);
        List<Product> products = query.getResultList();
        return products;
    }

    @Override
    public Product getProduct(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
    }

    @Override
    public int getSize(String category) {
        String sql = "from Product where category like :category and view=:view";
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery(sql, Product.class);
        query.setParameter("category", "%" + category + "%");
        query.setParameter("view", "yes");
        return query.getResultList().size();
    }

    @Override
    public List<Product> searchProducts(String search, int page) {
        List<Product> products;
        if (!search.equals("")) {
            String sql = "from Product where name like :search and view=:view";
            Session session = sessionFactory.getCurrentSession();
            Query<Product> query = session.createQuery(sql, Product.class);
            query.setParameter("search", "%" + search + "%");
            query.setParameter("view", "yes");
            products = query.getResultList();
        } else {
            products = new ArrayList<>();
        }
        return products;

    }

    @Override
    public void saveProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);
    }

    @Override
    public boolean nameExists(String name) {
        String sql = "from Product where name like :name";
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery(sql, Product.class);
        query.setParameter("name", name);
        int size = query.getResultList().size();
        return size != 0;
    }
}



