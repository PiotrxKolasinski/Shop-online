package com.onlineshop.service.customer;

import com.onlineshop.entity.Authorities;
import com.onlineshop.entity.Customer;

public interface CustomerService {

    void registerCustomer(Authorities customer);

    void saveCustomer(Customer customer);

    boolean userExists(String username);

    boolean emailExists(String email);

    Customer getCurrentCustomer();
}
