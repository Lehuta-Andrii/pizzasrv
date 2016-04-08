package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.order.Order;

public class InMemOrderRepository implements OrderRepository {

	List<Order> orders = new ArrayList<Order>();

	public int saveOrder(Order order) {
		orders.add(order);
		return order.getId();
	}

	@Override
	public boolean update(Order order) {
	    return true;
	}
	

}
