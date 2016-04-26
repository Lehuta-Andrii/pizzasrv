package org.study.pizzaservice.domain.order.state;

import java.time.LocalDate;
import java.util.List;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.OrderContext;
import org.study.pizzaservice.domain.order.OrderState;

/**
 * Class object that represents Done State of order. Dispatches all calls to
 * SimpleNoStateOrder object.
 * 
 * @author Andrii Lehuta
 *
 */
public class DoneState implements OrderState {

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
	public boolean addPizza(Pizza pizza) {
		return false;
	}

	@Override
	public boolean removePizza(Pizza pizza) {
		return false;
	}

	@Override
	public Customer getCustomer() {
		return order.getCustomer();
	}

	@Override
	public void setCustomer(Customer customer) {
	}

	@Override
	public List<Pizza> getPizzas() {
		return order.getPizzas();
	}

	@Override
	public void setPizzas(List<Pizza> pizzas) {
	}

	@Override
	public boolean canSetState(OrderState state) {
		if (state.getClass().equals(this.getClass())) {
			return true;
		}
		return false;
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DoneState";
	}

}
