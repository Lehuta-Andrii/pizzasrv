package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.customer.Customer;

@Repository
public class InMemCustomerRepository implements CustomerRepository {

    private List<Customer> customers = new ArrayList<Customer>();
    
    {
    	customers.add(new Customer("Abel"));
    	customers.add(new Customer("Albert"));
    	customers.add(new Customer("Nikola"));
    }
    
    
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
