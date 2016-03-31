package org.study.pizzaservice.domain.order;

import java.util.List;

import org.study.pizzaservice.domain.Discount;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Class object that represents New State of order. Dispatches all calls to
 * SimpleNoStateOrder object.
 * 
 * @author Andrii Lehuta
 *
 */
public class NewOrder implements OrderState {

    private SimpleNoStateOrder order;
 
    public void setContext(SimpleNoStateOrder order) {
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
    public Long getId() {
	return new Long(order.getId());
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
    public void setDiscount(Discount discount) {
	order.setDiscount(discount);
    }

}
