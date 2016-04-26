package org.study.pizzaservice.domain.order;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.state.NewState;
import org.study.pizzaservice.repository.jparepository.OrderStateConverter;

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
@Entity
@Access(AccessType.FIELD)
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Embedded
	private OrderContext order;

	@Transient
	private OrderState state;

	public Order(Customer customer, List<Pizza> pizzas, Address address) {
		this.order = new OrderContext(customer, pizzas, address);
		this.state = new NewState();
		state.setContext(order);
	}

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
	@Access(AccessType.PROPERTY)
	@Column(name = "state")
	@Convert(converter = OrderStateConverter.class)
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

	public Long getId() {
		return id;
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

	public LocalDate getDate() {
		return state.getDate();
	}

	public void setDate(LocalDate date) {
		state.setDate(date);
	}

	public Address getAddress() {
		return state.getAddress();
	}

	public void setAddress(Address address) {
		state.setAddress(address);
	}
	
	public OrderContext getOrderContext(){
		return order;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [id=" + id + ", order=" + order + ", state=" + state + "]";
	}

}
