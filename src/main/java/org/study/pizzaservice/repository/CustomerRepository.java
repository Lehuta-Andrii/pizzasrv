package org.study.pizzaservice.repository;

import java.util.List;

import org.study.pizzaservice.domain.customer.Customer;

public interface CustomerRepository {

    Customer getCostumerById(Integer id);

    List<Customer> getCostumers();

    boolean addCostumer(Customer costumer);

}
