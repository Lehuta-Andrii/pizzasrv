package org.study.pizzaservice.service;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.Order;

/**
 * Class that defines pizza shop using Template pattern
 * 
 * @author Andrii_Lehuta
 *
 */
public abstract class PizzaShopTemplate {

	public abstract List<Pizza> showMenu();

	public abstract Order makeOrder(Customer customer, Integer... pizzaIds);

	public abstract boolean confirmOrder(Order order);

	public abstract boolean cancelOrder(Order order);

	public abstract boolean accomplishOrder(Order order);

	public abstract double getPrice(Order order);

	public abstract double getDiscount(Order order);

	public abstract double getPriceWithDiscount(Order order);

}
