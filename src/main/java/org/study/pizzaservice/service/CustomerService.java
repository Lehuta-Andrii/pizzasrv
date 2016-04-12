package org.study.pizzaservice.service;

import java.util.List;

import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface for definition of Customer service in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public interface CustomerService {

    Customer getCostumerById(Integer id);

    List<Customer> getCostumers();

    boolean addCostumer(Customer customer);
    
    boolean removeCustomer(Customer customer);
    
    boolean addAddress(Customer customer, Address address);
    
    boolean removeAddress(Customer customer, Address address);

}
