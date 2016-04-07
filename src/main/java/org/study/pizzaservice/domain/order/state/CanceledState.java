package org.study.pizzaservice.domain.order.state;

import java.util.Collections;
import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.domain.order.OrderContext;
import org.study.pizzaservice.domain.order.OrderState;

/**
 * Class object that represents Canceled State of order. Dispatches all calls to
 * SimpleNoStateOrder object.
 * 
 * @author Andrii Lehuta
 *
 */
public class CanceledState implements OrderState {

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
    public int getId() {
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

}
