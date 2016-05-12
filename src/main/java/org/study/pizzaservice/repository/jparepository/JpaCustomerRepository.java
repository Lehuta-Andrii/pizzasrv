package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.CustomerRepository;

@Repository
public class JpaCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Customer> getCostumerById(Long id) {
		
		TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.getByCustomerId", Customer.class)
				.setParameter("customer_id", id);

		return Optional.of(query.getSingleResult());
	}

	@Override
	public List<Customer> getCustomers() {
		TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.getCustomers", Customer.class);
		return query.getResultList();
	}

	@Override
	public boolean addCustomer(Customer customer) {

		try {
			entityManager.persist(customer);
		} catch (PersistenceException ex) {
			System.err.println(ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		Customer dbCustomer = entityManager.find(Customer.class, customer.getId());
		if (dbCustomer != null) {			
			entityManager.merge(customer);
			return true;
		}

		return false;

	}

	@Override
	public boolean removeCustomer(Customer customer) {
		Customer dbCustomer = entityManager.find(Customer.class, customer.getId());
		if (dbCustomer != null) {
			entityManager.remove(dbCustomer);
			return true;
		}

		return false;

	}

}
