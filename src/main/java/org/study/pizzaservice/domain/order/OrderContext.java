package org.study.pizzaservice.domain.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
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

    @Transient
    private int pizzasAmmount;

    @ElementCollection
    private Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>();

    public OrderContext(Customer customer, List<Pizza> pizzas) {
	this.customer = customer;
	for (Pizza pizza : pizzas) {
	    addPizzaHelper(pizza);
	}
    }

    public OrderContext() {

    }

    /**
     * @return the amount of pizza objects in order
     */
    public int getPizzasAmount() {
	return pizzasAmmount;
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
	if (pizzas.containsKey(pizza)) {
	    if (pizzas.get(pizza) == 1) {
		pizzas.remove(pizza);
	    } else {
		pizzas.put(pizza, pizzas.get(pizza) - 1);
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
	
	for (Pizza key : pizzas.keySet()) {
	    for (int i = 0; i < pizzas.get(key); i++) {
		result.add(key);
	    }
	}

	return result;
    }

    /**
     * @param pizzas
     *            the pizzas to set
     */
    public void setPizzas(List<Pizza> pizzas) {
	pizzas.clear();
	pizzasAmmount = 0;

	for (Pizza pizza : pizzas) {
	    addPizzaHelper(pizza);
	}
    }

    @Override
    public String toString() {
	return "Order [customer=" + customer + ", pizzas=" + pizzas.keySet() + "]";
    }

    private boolean addPizzaHelper(Pizza pizza) {

	if (pizzas.containsKey(pizza)) {
	    pizzas.put(pizza, pizzas.get(pizza) + 1);
	} else {
	    pizzas.put(pizza, 1);
	}

	pizzasAmmount++;

	return true;
    }
}
