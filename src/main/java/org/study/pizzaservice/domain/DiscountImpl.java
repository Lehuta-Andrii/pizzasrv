package org.study.pizzaservice.domain;

import org.study.pizzaservice.domain.order.Order;

/**
 * Implementation of discount entity. Discount consists of 10 percent of sum of
 * accumulative card of Customer(if available) or 30 percent of order price if
 * 10 percent sum of accumulative card is bigger than 30 percent of order price.
 * Also if amount of pizzas in order more than 4 the 30 percent of most
 * expensive pizza price will be added to discount.
 * 
 * @author Andrii Lehuta
 *
 */
public class DiscountImpl implements Discount {

    @Override
    public double getDiscount(Order order) {
	double result = 0;
	double price = order.getPrice();
	double mostExpensivePizza = 0;

	for (Pizza pizza : order.getPizzas()) {
	    if (pizza.getPrice() > mostExpensivePizza) {
		mostExpensivePizza = pizza.getPrice();
	    }
	}

	if (order.getPizzasAmount() >= 4) {
	    result += mostExpensivePizza * 0.30;
	    price -= result;
	}

	if (order.getCustomer().hasAccumulativeCard()) {
	    double AccummulativeCardDiscount = order.getCustomer().getAccumulativeCard().getSum() * 0.1;

	    if (Double.compare(AccummulativeCardDiscount, price * 0.3) < 0) {
		result += AccummulativeCardDiscount;
	    } else {
		result += price * 0.3;
	    }
	}

	return result;

    }

}
