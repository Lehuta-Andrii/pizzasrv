package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.study.pizzaservice.domain.customer.Customer;

public class InMemCustomerRepository implements CustomerRepository {

    private List<Customer> customers = new ArrayList<Customer>();
    
    @Override
    public Customer getCostumerById(Integer id) {
	for (Customer customer : customers) {
	    if (id.equals(customer.getId())) {
		return customer;
	    }
	}
	
	return null;
    }

    @Override
    public List<Customer> getCostumers() {
	return Collections.unmodifiableList(customers);
    }

    @Override
    public boolean addCostumer(Customer customer) {
	return customers.add(customer);
    }

}
