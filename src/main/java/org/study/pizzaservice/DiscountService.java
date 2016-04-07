package org.study.pizzaservice;

import java.util.List;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.discount.Discount;
import org.study.pizzaservice.domain.order.Order;

public interface DiscountService {

    public boolean addDiscount(Discount discount);

    public void setDiscounts(List<Discount> discounts);

    public List<Discount> getDiscounts();

    public double countDiscount(Order order, AccumulativeCard accumulativeCard);

    public double countDiscount(Order order, AccumulativeCard accumulativeCard, List<Discount> discounts);
}
