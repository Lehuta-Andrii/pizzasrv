package org.study.pizzaservice.domain.discount;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;

/**
 * Interface that represents discount entity of pizza service
 * 
 * @author Lehuta Andrii
 *
 */
public interface Discount {
    double getDiscount(List<Pizza> pizzas, AccumulativeCardImpl accumulativeCard);
}
