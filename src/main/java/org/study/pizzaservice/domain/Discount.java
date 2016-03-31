package org.study.pizzaservice.domain;

import org.study.pizzaservice.domain.order.Order;

/**
 * Interface that represents discount entity of pizza service
 * 
 * @author Lehuta Andrii
 *
 */
public interface Discount {
    double getDiscount(Order order);
}
