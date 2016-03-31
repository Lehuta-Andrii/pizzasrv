package org.study.pizzaservice.domain.order;

import java.util.Collections;
import java.util.List;

import org.study.pizzaservice.domain.Discount;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Class object that represents Done State of order. Dispatches all calls to
 * SimpleNoStateOrder object.
 * 
 * @author Andrii Lehuta
 *
 */
public class DoneOrder implements OrderState {

    private SimpleNoStateOrder order;
    
    @Override
    public void setContext(SimpleNoStateOrder order) {
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
    public Long getId() {
	return order.getId();
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
	return Collections.unmodifiableList(order.getPizzas());
    }

    @Override
    public void setPizzas(List<Pizza> pizzas) {	
    }

    @Override
    public void setDiscount(Discount discount) {	
    }

}
