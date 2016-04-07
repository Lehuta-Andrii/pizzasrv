package org.study.pizzaservice;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;

public abstract class PizzaShopTemplate {

	public abstract List<Pizza> showMenu();

	public abstract Order makeOrder(Integer... pizzaIds);

	public abstract Order makeOrder(Customer customer, Integer... pizzaIds);

	public abstract boolean confirmOrder(Order order);
	
	public abstract boolean cancelOrder(Order order);
	
	public abstract boolean accomplishOrder(Order order);

	public abstract double getPrice();

	public abstract double getDiscount();

}
