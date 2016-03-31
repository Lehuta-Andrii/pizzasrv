package org.study.pizzaservice.domain.order;

import java.util.List;

import org.study.pizzaservice.domain.Discount;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Order class represents order with states object of service. Dispatches all
 * calls to SimpleNoStateOrder object except methods that count price and
 * discount
 * 
 * @author Andrii Lehuta
 *
 */
public class Order {

    private SimpleNoStateOrder order;
    private OrderState state;

    public Order(Customer customer, List<Pizza> pizzas) {
	this.order = new SimpleNoStateOrder(customer, pizzas);
	this.state = new NewOrder();
	state.setContext(order);
    }

    /**
     * @return the state of order
     */
    public OrderState getState() {
	return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(OrderState state) {
	this.state = state;
	this.state.setContext(order);
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
	return state.getId();
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

    public void setDiscount(Discount discount) {
	state.setDiscount(discount);
    }

    public double getDiscount() {
	if (order.getDiscountObject() != null) {
	    return order.getDiscountObject().getDiscount(this);
	}

	return 0;
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

    /**
     * @return the price of order with discount
     */
    public double getPriceWithDiscount() {
	return getPrice() + getDiscount();
    }

    /**
     * Adds the sum of price to customer Accumulative card only if order in done
     * state
     * 
     * @return true if accumulative card of customer is updated
     */
    public boolean updateCustomerCard() {
	if (state.getClass().equals(DoneOrder.class)) {
	    if (order.getCustomer().hasAccumulativeCard()) {
		order.getCustomer().getAccumulativeCard().addToCard(getPriceWithDiscount());
		return true;
	    }
	}

	return false;
    }

    @Override
    public String toString() {
	return order.toString();
    }
}
