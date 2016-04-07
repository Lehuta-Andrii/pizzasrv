package org.study.pizzaservice.domain.order;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.state.NewState;

/**
 * Order class represents order with states object of service. Dispatches all
 * calls to SimpleNoStateOrder object except methods that count price and
 * discount
 * 
 * @author Andrii Lehuta
 *
 */
public class Order {

	private OrderContext order;
	private OrderState state;

	public Order(Customer customer, List<Pizza> pizzas) {
		this.order = new OrderContext(customer, pizzas);
		this.state = new NewState();
		state.setContext(order);
	}

	/**
	 * @return the state of order
	 */
	public OrderState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(OrderState state) {
		this.state = state;
		this.state.setContext(order);
	}

	public int getPizzasAmount() {
		return state.getPizzasAmount();
	}

	public boolean addPizza(Pizza pizza) {
		return state.addPizza(pizza);
	}

	public boolean removePizza(Pizza pizza) {
		return state.removePizza(pizza);
	}

	public int getId() {
		return state.getId();
	}

	public Customer getCustomer() {
		return state.getCustomer();
	}

	public void setCustomer(Customer customer) {
		state.setCustomer(customer);
	}

	public List<Pizza> getPizzas() {
		return state.getPizzas();
	}

	public void setPizzas(List<Pizza> pizzas) {
		state.setPizzas(pizzas);
	}

	/**
	 * @return the price of order
	 */
	public double getPrice() {
		double price = 0;

		for (Pizza pizza : order.getPizzas()) {
			price += pizza.getPrice();
		}

		return price;
	}

	@Override
	public String toString() {
		return order.toString();
	}
}
