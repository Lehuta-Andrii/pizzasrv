package org.study.pizzaservice.service.simpleservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.exceptions.CustomerWithSpecificIdIsAbsentException;
import org.study.pizzaservice.repository.CustomerRepository;
import org.study.pizzaservice.service.CustomerService;

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
	public Customer getCustomerById(Long id) {
		Optional<Customer> customer = customers.getCostumerById(id);
		if(customer.isPresent()){
		    return customer.get();
		}
		
		throw new CustomerWithSpecificIdIsAbsentException();
	}

	@Override
	public List<Customer> getCustomers() {
		return customers.getCostumers();
	}

	@Override
	public boolean addCustomer(Customer costumer) {
		return customers.addCustomer(costumer);
	}

	@Override
	public boolean addAddress(Customer customer, Address address) {
	    if(customer.addAddress(address)){
		return customers.updateCustomer(customer);
	    }else{
		return false;
	    }
	}

	@Override
	public boolean removeAddress(Customer customer, Address address) {
	    if(customer.removeAddress(address)){
		return customers.updateCustomer(customer);
	    }else{
		return false;
	    }
	}

	@Override
	public boolean removeCustomer(Customer customer) {
	    return customers.removeCustomer(customer);
	}

}
