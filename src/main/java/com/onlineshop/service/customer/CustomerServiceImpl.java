package com.onlineshop.service.customer;

import com.onlineshop.dao.customer.CustomerDAO;
import com.onlineshop.entity.Authorities;
import com.onlineshop.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }


    public CustomerServiceImpl() {
    }

    @Override
    @Transactional
    public void registerCustomer(Authorities customer) {
        customerDAO.registerCustomer(customer);
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
    }

    @Override
    @Transactional
    public boolean userExists(String username) {
        return  customerDAO.userExists(username);
    }

    @Override
    @Transactional
    public boolean emailExists(String email) {
        return customerDAO.emailExists(email);
    }

    @Override
    @Transactional
    public Customer getCurrentCustomer() {
        return customerDAO.getCurrentCustomer();
    }
}
