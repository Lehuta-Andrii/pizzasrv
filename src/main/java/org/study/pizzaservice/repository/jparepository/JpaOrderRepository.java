package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.repository.OrderRepository;

@Repository
@Transactional
public class JpaOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean saveOrder(Order order) {
	try {
	    entityManager.persist(order);
	} catch (PersistenceException ex) {
	    System.err.println(ex);
	    return false;
	}
	return true;
    }

    @Override
    public boolean update(Order order) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Order> getOrders() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean removeOrder(Order order) {
	// TODO Auto-generated method stub
	return false;
    }

}
