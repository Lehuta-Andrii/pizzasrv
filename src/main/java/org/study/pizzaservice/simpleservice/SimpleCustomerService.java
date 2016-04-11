package org.study.pizzaservice.simpleservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pizzaservice.CustomerService;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.exceptions.CustomerWithSpecificIdIsAbsentException;
import org.study.pizzaservice.repository.CustomerRepository;

/**
 * Class represent customer service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
public class SimpleCustomerService implements CustomerService {

	private CustomerRepository customers;

	@Autowired
	public SimpleCustomerService(CustomerRepository customers) {
		this.customers = customers;
	}

	@Override
	public Customer getCostumerById(Integer id) {
		Optional<Customer> customer = customers.getCostumerById(id);
		if(customer.isPresent()){
		    return customer.get();
		}
		
		throw new CustomerWithSpecificIdIsAbsentException();
	}

	@Override
	public List<Customer> getCostumers() {
		return customers.getCostumers();
	}

	@Override
	public boolean addCostumer(Customer costumer) {
		return customers.addCostumer(costumer);
	}

	@Override
	public boolean addAddress(Customer customer, Address address) {
	    if(customer.addAddress(address)){
		return customers.updateCostumer(customer);
	    }else{
		return false;
	    }
	}

	@Override
	public boolean removeAddress(Customer customer, Address address) {
	    if(customer.removeAddress(address)){
		return customers.updateCostumer(customer);
	    }else{
		return false;
	    }
	}

	@Override
	public boolean removeCustomer(Customer customer) {
	    return customers.removeCostumer(customer);
	}

}
