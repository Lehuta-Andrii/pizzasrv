package org.study.pizzaservice.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;

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
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Pizza> pizzas;
	
	public OrderContext(Customer customer, List<Pizza> pizzas) {
		this.customer = customer;
		this.pizzas = new ArrayList<Pizza>(pizzas);
	}
	
	public OrderContext(){
	}

	/**
	 * @return the amount of pizza objects in order
	 */
	public int getPizzasAmount() {
		return pizzas.size();
	}

	/**
	 * Add one pizza object to order
	 * 
	 * @param pizza
	 *            pizza object to add
	 * @return true if pizza object was added
	 */
	public boolean addPizza(Pizza pizza) {
		return pizzas.add(pizza);
	}

	/**
	 * Remove one pizza object to order
	 * 
	 * @param pizza
	 *            pizza object to add
	 * @return true if pizza object was removed
	 */

	public boolean removePizza(Pizza pizza) {
		return pizzas.remove(pizza);

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
		return pizzas;
	}

	/**
	 * @param pizzas
	 *            the pizzas to set
	 */
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	@Override
	public String toString() {
		return "Order [customer=" + customer + ", pizzas=" + pizzas + "]";
	}
}
