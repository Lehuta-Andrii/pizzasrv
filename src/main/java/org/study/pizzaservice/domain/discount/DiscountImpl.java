package org.study.pizzaservice.domain.discount;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;

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
	public double getDiscount(List<Pizza> pizzas, AccumulativeCard accumulativeCard) {
		double result = 0;
		double mostExpensivePizza = 0;
		double price = 0;

		for (Pizza pizza : pizzas) {
			if (pizza.getPrice() > mostExpensivePizza) {
				mostExpensivePizza = pizza.getPrice();
			}
			price += pizza.getPrice();
		}

		if (pizzas.size() >= 4) {
			result += mostExpensivePizza * 0.30;
			price -= result;
		}

		if (accumulativeCard != null) {
			double AccummulativeCardDiscount = accumulativeCard.getSum() * 0.1;

			if (Double.compare(AccummulativeCardDiscount, price * 0.3) < 0) {
				result += AccummulativeCardDiscount;
			} else {
				result += price * 0.3;
			}
		}

		return result;

	}

}
