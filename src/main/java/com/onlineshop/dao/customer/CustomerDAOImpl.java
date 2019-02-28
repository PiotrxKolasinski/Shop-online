package com.onlineshop.dao.customer;

import com.onlineshop.entity.Authorities;
import com.onlineshop.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CustomerDAOImpl implements CustomerDAO{
    private final SessionFactory sessionFactory;

    @Autowired
    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void registerCustomer(Authorities customer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(customer);
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.update(customer);
    }

    @Override
    public boolean userExists(String username) {
        String sql = "from Customer where username LIKE :username";
        return isExists(username, sql, "username");
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "from Customer where email LIKE :email";
        return isExists(email, sql, "email");
    }

    private boolean isExists(String email, String sql, String email2) {
        Session session = sessionFactory.getCurrentSession();
        Query<Customer> query = session.createQuery(sql, Customer.class);
        query.setParameter(email2, email);
        int size = query.getResultList().size();
        return size != 0;
    }

    @Override
    public Customer getCurrentCustomer() {
        String sql = "from Customer where username=:username";
        Session session = sessionFactory.getCurrentSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Query<Customer> query = session.createQuery(sql, Customer.class);
        query.setParameter("username", username);
        List<Customer> customer = query.getResultList();
        return customer.get(0);
    }

}
