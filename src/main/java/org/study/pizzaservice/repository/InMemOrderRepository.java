package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.Order;

@Repository("orderRepository")
public class InMemOrderRepository implements OrderRepository {

    List<Order> orders = new ArrayList<Order>();
    
    public Long saveOrder(Order order) {
	orders.add(order);
	return order.getId();
    }

}
