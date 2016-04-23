package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.CustomerRepository;

@Repository
@Transactional
public class JpaCustomerRepository implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Customer> getCostumerById(Long id) {
	return Optional.of(entityManager.find(Customer.class, id));
    }

    @Override
    public List<Customer> getCostumers() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean addCustomer(Customer customer) {
	persistAddresses(customer);
	entityManager.persist(customer);
	return true;
    }

    private void persistAddresses(Customer customer) {
	for (Address address : customer.getAddresses()) {
	    entityManager.persist(address);
	}
    }

    @Override
    public boolean updateCustomer(Customer customer) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeCustomer(Customer customer) {
	// TODO Auto-generated method stub
	return false;
    }

}
