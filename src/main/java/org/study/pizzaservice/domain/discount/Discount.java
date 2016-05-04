package org.study.pizzaservice.domain.discount;

import java.util.Map;
import java.util.Optional;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;

/**
 * Interface that represents discount entity of pizza service
 * 
 * @author Lehuta Andrii
 *
 */
public interface Discount {
	double getDiscount(Map<Pizza, Integer> pizzas, Optional<AccumulativeCard> accumulativeCard);
}
