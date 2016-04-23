package org.study.pizzaservice.service;

import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.order.OrderState;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface for definition of Order service in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public interface OrderService {
	Order placeNewOrder(Customer customer, Long... pizzasID);
	
	boolean removeOrder(Order order);

	boolean changeOrderStatus(Order order, OrderState state);

	boolean confirmOrder(Order order);

	boolean cancelOrder(Order order);

	boolean accomplishOrder(Order order);
	
	Order gerOrderById(Long id);

}