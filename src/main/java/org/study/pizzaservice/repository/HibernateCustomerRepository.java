package org.study.pizzaservice.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.customer.Customer;

@Repository
@Transactional
public class HibernateCustomerRepository implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Customer> getCostumerById(Integer id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Customer> getCostumers() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean addCostumer(Customer customer) {
	try {
	    entityManager.getTransaction().begin();
	    entityManager.persist(customer);
	    entityManager.getTransaction().commit();
	    entityManager.flush();
	} finally {
	    return true;
	}
    }

    @Override
    public boolean updateCostumer(Customer customer) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeCostumer(Customer customer) {
	// TODO Auto-generated method stub
	return false;
    }

}
