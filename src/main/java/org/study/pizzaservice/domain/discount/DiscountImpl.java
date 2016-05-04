package org.study.pizzaservice.domain.discount;

import java.util.Map;
import java.util.Optional;

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

    private static final double ACCUMULATIVE_CARD_MULTIPLIER = 0.1;
    private static final int PIZZAS_NUMBER_FOR_DISCOUNT = 4;
    private static final double AFTER_FOURTH_PIZZA_DISCOUNT = 0.30;

    @Override
    public double getDiscount(Map<Pizza, Integer> pizzas, Optional<AccumulativeCard> accumulativeCard) {
	double result = 0;
	double mostExpensivePizza = 0;
	double price = 0;

	for (Pizza pizza : pizzas.keySet()) {
	    if (pizza.getPrice() > mostExpensivePizza) {
		mostExpensivePizza = pizza.getPrice();
	    }
	    price += pizza.getPrice();
	}
	
	int pizzasAmmount = 0;
	for (int amount : pizzas.values()) {
		pizzasAmmount += amount;
	}

	if (pizzasAmmount >= PIZZAS_NUMBER_FOR_DISCOUNT) {
	    result += mostExpensivePizza * AFTER_FOURTH_PIZZA_DISCOUNT;
	    price -= result;
	}

	result = countDiscountUsingAccumulativeCard(accumulativeCard, result, price);

	return result;

    }

    private double countDiscountUsingAccumulativeCard(Optional<AccumulativeCard> accumulativeCard, double result, double price) {

	if (accumulativeCard.isPresent()) {

	    double AccummulativeCardDiscount = accumulativeCard.get().getSum() * ACCUMULATIVE_CARD_MULTIPLIER;

	    if (Double.compare(AccummulativeCardDiscount, price * AFTER_FOURTH_PIZZA_DISCOUNT) < 0) {
		result += AccummulativeCardDiscount;
	    } else {
		result += price * AFTER_FOURTH_PIZZA_DISCOUNT;
	    }
	}

	return result;
    }

}
