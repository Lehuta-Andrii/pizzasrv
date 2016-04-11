package org.study.pizzaservice.domain.order.state;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.OrderContext;
import org.study.pizzaservice.domain.order.OrderState;

/**
 * Class object that represents New State of order. Dispatches all calls to
 * SimpleNoStateOrder object.
 * 
 * @author Andrii Lehuta
 *
 */
public class NewState implements OrderState {

    private OrderContext order;

    public void setContext(OrderContext order) {
	this.order = order;
    }

    @Override
    public int getPizzasAmount() {
	return order.getPizzasAmount();
    }

    @Override
    public boolean addPizza(Pizza pizza) {
	return order.addPizza(pizza);
    }

    @Override
    public boolean removePizza(Pizza pizza) {
	return order.removePizza(pizza);
    }

    @Override
    public int getId() {
	return order.getId();
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
    public List<Pizza> getPizzas() {
	return order.getPizzas();
    }

    @Override
    public void setPizzas(List<Pizza> pizzas) {
	order.setPizzas(pizzas);
    }

    @Override
    public boolean canSetState(OrderState state) {
	if (state.getClass().equals(this.getClass())) {
	    return false;
	} else {
	    return true;
	}
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "NewState";
    }

    
}
