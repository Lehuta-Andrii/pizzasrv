package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Dummy implementation of Customer repository entity
 * 
 * @author Andrii Lehuta
 *
 */
//@Repository
public class InMemCustomerRepository implements CustomerRepository {

    private List<Customer> customers = new ArrayList<Customer>();

    {
	customers.add(new Customer(1l,"Abel"));
	customers.add(new Customer(2l,"Albert"));
	customers.add(new Customer(3l,"Nikola"));
    }

    @Override
    public Optional<Customer> getCostumerById(Integer id) {
	for (Customer customer : customers) {
	    if (id.equals(customer.getId())) {
		return Optional.<Customer>of(customer);
	    }
	}

	return Optional.<Customer>empty();
    }

    @Override
    public List<Customer> getCostumers() {
	return customers;
    }

    @Override
    public boolean addCostumer(Customer customer) {
	return customers.add(customer);
    }

    @Override
    public boolean updateCostumer(Customer customer) {
	return true;
    }

    @Override
    public boolean removeCostumer(Customer customer) {
	return customers.remove(customer);
    }

}
