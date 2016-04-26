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
@Access(AccessType.FIELD)
public class OrderContext {

	@OneToOne
	private Customer customer;

	@Transient
	private int pizzasAmmount;

	@Column(name = "date")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate date;

	@OneToOne
	private Address address;

	//@ElementCollection(fetch = FetchType.EAGER)
	@Transient
	private Map<Pizza, Integer> pizzasMap = new HashMap<Pizza, Integer>();

	public OrderContext(Customer customer, List<Pizza> pizzas) {
		this.date = LocalDate.now();
		this.customer = customer;
		for (Pizza pizza : pizzas) {
			addPizzaHelper(pizza);
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
	 * Add one pizza object to order
	 * 
	 * @param pizza
	 *            pizza object to add
	 * @return true if pizza object was added
	 */
	public boolean addPizza(Pizza pizza) {
		return addPizzaHelper(pizza);
	}

	/**
	 * Remove one pizza object to order
	 * 
	 * @param pizza
	 *            pizza object to add
	 * @return true if pizza object was removed
	 */

	public boolean removePizza(Pizza pizza) {
		if (pizzasMap.containsKey(pizza)) {
			if (pizzasMap.get(pizza) == 1) {
				pizzasMap.remove(pizza);
			} else {
				pizzasMap.put(pizza, pizzasMap.get(pizza) - 1);
			}
		} else {
			return false;
		}

		pizzasAmmount--;
		return true;
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

	/**
	 * @return the pizzas
	 */
	public List<Pizza> getPizzas() {
		List<Pizza> result = new ArrayList<Pizza>();

		for (Pizza key : pizzasMap.keySet()) {
			for (int i = 0; i < pizzasMap.get(key); i++) {
				result.add(key);
			}
		}

		return result;
	}
	
	@Access(AccessType.PROPERTY)
	@ElementCollection(fetch = FetchType.EAGER)
	public Map<Pizza, Integer> getPizzasMap(){
		return pizzasMap;
	}
	
	public void setPizzasMap(Map<Pizza, Integer> pizzaMap){
		this.pizzasMap = pizzaMap;
	
		for(int amount : pizzasMap.values()){
			this.pizzasAmmount += amount;
		}
	}

	/**
	 * @param pizzas
	 *            the pizzas to set
	 */
	public void setPizzas(List<Pizza> pizzas) {
		pizzasMap.clear();
		pizzasAmmount = 0;

		for (Pizza pizza : pizzas) {
			addPizzaHelper(pizza);
		}
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderContext [customer=" + customer + ", pizzasAmmount=" + pizzasAmmount + ", date=" + date
				+ ", address=" + address + ", pizzas=" + pizzasMap + "]";
	}

	private boolean addPizzaHelper(Pizza pizza) {

		if (pizzasMap.containsKey(pizza)) {
			pizzasMap.put(pizza, pizzasMap.get(pizza) + 1);
		} else {
			pizzasMap.put(pizza, 1);
		}

		pizzasAmmount++;

		return true;
	}
}
