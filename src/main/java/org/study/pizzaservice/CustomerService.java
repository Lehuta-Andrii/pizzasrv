package org.study.pizzaservice;

import java.util.List;

import org.study.pizzaservice.domain.customer.Customer;

public interface CustomerService {

    Customer getCostumerById(Integer id);

    List<Customer> getCostumers();

    boolean addCostumer(Customer costumer);

}
