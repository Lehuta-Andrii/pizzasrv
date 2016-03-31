package org.study.pizzaservice;

import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;

public class PizzaApp {

    public static void main(String[] args) {
	Customer customer = null;
	Order order;

	OrderService orderService = new SimpleOrderService();
	order = orderService.placeNewOrder(customer, 0, 1, 2);

	System.out.println(order);

    }

}
