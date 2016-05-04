package org.study.pizzaservice.domain.order.state;

import java.time.LocalDate;
import java.util.Map;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.OrderContext;
import org.study.pizzaservice.domain.order.OrderState;

/**
 * Class object that represents In progress State of order. Dispatches all calls
 * to SimpleNoStateOrder object.
 * 
 * @author Andrii Lehuta
 *
 */
public class InProgressState implements OrderState {

	private OrderContext order;

	@Override
	public void setContext(OrderContext order) {
		this.order = order;
	}

	@Override
	public int getPizzasAmount() {
		return order.getPizzasAmount();
	}

	@Override
	public boolean addPizza(Pizza pizza, int amount) {
		return false;
	}

	@Override
	public boolean removePizza(Pizza pizza, int amount) {
		return false;
	}

	@Override
	public Customer getCustomer() {
		return order.getCustomer();
	}

	@Override
	public void setCustomer(Customer customer) {
		order.setCustomer(customer);
	}

	@Override
	public Map<Pizza, Integer> getPizzasMap() {
		return order.getPizzasMap();
	}

	@Override
	public void setPizzasMap(Map<Pizza, Integer> pizzas) {
	}

	@Override
	public boolean canSetState(OrderState state) {
		if (state.getClass().equals(NewState.class)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public LocalDate getDate() {
		return order.getDate();
	}

	@Override
	public void setDate(LocalDate date) {
		order.setDate(date);
	}

	@Override
	public Address getAddress() {
		return order.getAddress();
	}

	@Override
	public void setAddress(Address address) {
		order.setAddress(address);
	}

	@Override
	public int getAmountOfPizza(Pizza pizza) {
		return order.getAmountOfPizza(pizza);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InProgressState";
	}

}
