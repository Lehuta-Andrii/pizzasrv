package org.study.pizzaservice.simpleservice;

import java.util.List;

import org.study.pizzaservice.CustomerService;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.CustomerRepository;

public class SimpleCustomerService implements CustomerService {

    private CustomerRepository customers;
    
    public SimpleCustomerService(CustomerRepository customers) {
	this.customers = customers;
    }

    @Override
    public Customer getCostumerById(Integer id) {
	return customers.getCostumerById(id);
    }

    @Override
    public List<Customer> getCostumers() {
	return customers.getCostumers();
    }

    @Override
    public boolean addCostumer(Customer costumer) {
	return customers.addCostumer(costumer);
    }

}
