package com.onlineshop.service.customer;

import com.onlineshop.dao.customer.CustomerDAO;
import com.onlineshop.entity.Authorities;
import com.onlineshop.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class.getName());

    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    @Transactional
    public void registerCustomer(Authorities customer) {
        customerDAO.registerCustomer(customer);
        logger.info("[CustomerService] register customer with id: " + customer.getId());
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
        logger.info("[CustomerService] save customer with id: " + customer.getId());
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
