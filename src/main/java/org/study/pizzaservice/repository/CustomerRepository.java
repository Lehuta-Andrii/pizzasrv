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

    Optional<Customer> getCostumerById(Integer id);

    List<Customer> getCostumers();

    boolean addCostumer(Customer customer);
    
    boolean updateCostumer(Customer customer);
    
    boolean removeCostumer(Customer customer);

}
