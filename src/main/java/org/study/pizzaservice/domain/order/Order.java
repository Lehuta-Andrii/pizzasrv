package org.study.pizzaservice.domain.order;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
@Component
@Scope("prototype")
public class Order {

	private OrderContext order;
	private OrderState state;

	public Order(Customer customer, List<Pizza> pizzas) {
		this.order = new OrderContext(customer, pizzas);
		this.state = new NewState();
		state.setContext(order);
	}

	public Order() {
		this.order = new OrderContext();
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
	public boolean setState(OrderState state) {
		if (this.state.canSetState(state)) {
			this.state = state;
			this.state.setContext(order);
			return true;
		} else {
			return false;
		}

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
