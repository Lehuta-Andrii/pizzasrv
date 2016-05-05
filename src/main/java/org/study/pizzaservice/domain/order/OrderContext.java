package org.study.pizzaservice.domain.order;

import java.util.*;
import javax.persistence.*;
import java.time.LocalDate;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.jparepository.LocalDateConverter;

/**
 * Class SimpleNoStateOrder keeps all information about order except of the
 * state of order
 * 
 * @author Andrii Lehuta
 *
 */
@Embeddable
public class OrderContext {

	@OneToOne
	private Customer customer;

	private int pizzasAmmount;

	@Column(name = "date")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate date;

	@OneToOne
	private Address address;

	@ElementCollection(fetch = FetchType.LAZY)
	@Column(name = "pizzas_ammount")
	private Map<Pizza, Integer> pizzasMap = new HashMap<Pizza, Integer>();

	public OrderContext(Customer customer, List<Pizza> pizzas) {
		this.date = LocalDate.now();
		this.customer = customer;
		for (Pizza pizza : pizzas) {
			addPizzaHelper(pizza, 1);
		}
	}

	public OrderContext(Customer customer, List<Pizza> pizzas, Address address) {
		this(customer, pizzas);
		this.address = address;
	}

	public OrderContext() {
		this.date = LocalDate.now();
	}

	/**
	 * @return the amount of pizza objects in order
	 */
	public int getPizzasAmount() {
		return pizzasAmmount;
	}

	/**
	 * @return the amount of certain pizza objects in order
	 */
	public int getAmountOfPizza(Pizza pizza) {
		if (pizzasMap.get(pizza) != null) {
			return pizzasMap.get(pizza);
		}

		return 0;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Add number of pizzas object to order
	 * 
	 * @param pizza
	 *            pizza object to add
	 * @return true if pizza object was added
	 */
	public boolean addPizza(Pizza pizza, int amount) {
		return addPizzaHelper(pizza, amount);
	}

	/**
	 * Remove number pizzas object to order
	 * 
	 * @param pizza
	 *            pizza object to add
	 * @return true if pizza object was removed
	 */

	public boolean removePizza(Pizza pizza, int amount) {
		if (pizzasMap.containsKey(pizza)) {
			if (pizzasMap.get(pizza) == amount) {
				pizzasMap.remove(pizza);
				pizzasAmmount -= amount;
				return true;
			} else if (pizzasMap.get(pizza) < amount) {
				pizzasMap.put(pizza, pizzasMap.get(pizza) - amount);
				pizzasAmmount -= amount;
				return true;
			} else if (pizzasMap.get(pizza) > amount) {
				return false;
			}
		}

		return false;

	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Map<Pizza, Integer> getPizzasMap() {
		return pizzasMap;
	}

	public void setPizzasMap(Map<Pizza, Integer> pizzaMap) {
		this.pizzasMap = pizzaMap;

		for (int amount : pizzasMap.values()) {
			this.pizzasAmmount += amount;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderContext [customer=" + customer + ", pizzasAmmount=" + pizzasAmmount + ", date=" + date
				+ ", address=" + address + ", pizzas=" + pizzasMap + "]";
	}

	private boolean addPizzaHelper(Pizza pizza, int amount) {

		if (pizzasMap.containsKey(pizza)) {
			pizzasMap.put(pizza, pizzasMap.get(pizza) + amount);
		} else {
			pizzasMap.put(pizza, amount);
		}

		pizzasAmmount += amount;

		return true;
	}
}
