package org.study.pizzaservice.repository;

import java.util.List;
import java.util.Optional;

import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface that represents repository of customers entity
 * 
 * @author Andrii Lehuta
 *
 */
public interface CustomerRepository {

    Optional<Customer> getCostumerById(Long id);

    List<Customer> getCostumers();

    boolean addCustomer(Customer customer);
    
    boolean updateCustomer(Customer customer);
    
    boolean removeCustomer(Customer customer);

}
