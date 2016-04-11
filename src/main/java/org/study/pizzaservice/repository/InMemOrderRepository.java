package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.order.Order;

/**
 * Dummy implementation of Order repository entity
 * 
 * @author Andrii Lehuta
 *
 */
@Repository
public class InMemOrderRepository implements OrderRepository {

    private List<Order> orders = new ArrayList<Order>();

    @Override
    public boolean saveOrder(Order order) {
	return orders.add(order);
    }

    @Override
    public boolean update(Order order) {
	return true;
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {

	for (Order order : orders) {
	    if (id.compareTo(order.getId()) == 0) {
		return Optional.<Order>of(order);
	    }
	}

	return Optional.<Order>empty();
    }

    @Override
    public List<Order> getOrders() {
	return orders;
    }

    @Override
    public boolean removeOrder(Order order) {
	return orders.remove(order);
    }
    
    

}
