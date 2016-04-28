package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
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
		TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :order_id", Order.class)
				.setParameter("order_id", order.getId());

		
		Order dbOrder = query.getSingleResult();
		if (dbOrder != null) {
			
			entityManager.merge(order);
	
			return true;
		}
		return false;

	}

	@Override
	public Optional<Order> getOrderById(Long id) {

		TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :order_id", Order.class)
				.setParameter("order_id", id);

		return Optional.of(query.getSingleResult());

	}

	@Override
	public List<Order> getOrders() {
		TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
		return query.getResultList();
	}

	@Override
	public boolean removeOrder(Order order) {
		TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :order_id", Order.class)
				.setParameter("order_id", order.getId());

		Order dbOrder = query.getSingleResult();
		if (dbOrder != null) {
			entityManager.remove(dbOrder);
			return true;
		}

		return false;
	}

}
