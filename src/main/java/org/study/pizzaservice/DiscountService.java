package org.study.pizzaservice;

import java.util.List;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.discount.Discount;
import org.study.pizzaservice.domain.order.Order;

/**
 * Interface for definition of Discount service in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public interface DiscountService {

    boolean addDiscount(Discount discount);

    void setDiscounts(List<Discount> discounts);

    List<Discount> getDiscounts();

    double countDiscount(Order order, AccumulativeCard accumulativeCard);

    double countDiscount(Order order, AccumulativeCard accumulativeCard, List<Discount> discounts);
}
