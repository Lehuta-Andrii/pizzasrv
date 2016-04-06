package org.study.pizzaservice.simpleservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.study.pizzaservice.DiscountService;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.discount.Discount;
import org.study.pizzaservice.domain.discount.DiscountImpl;
import org.study.pizzaservice.domain.order.Order;

public class SimpleDiscountService implements DiscountService {

	List<Discount> discounts = new ArrayList<Discount>();
	
	{
	    discounts.add(new DiscountImpl());
	}
	
	@Override
	public boolean addDiscount(Discount discount) {
		if (discounts.contains(discount)) {
			return false;
		} else {
			return discounts.add(discount);
		}
	}

	@Override
	public void setDiscounts(List<Discount> discounts) {
		this.discounts = new ArrayList<Discount>(discounts);
	}

	@Override
	public List<Discount> getDiscounts() {
		return Collections.unmodifiableList(discounts);
	}

	@Override
	public double countDiscount(Order order, AccumulativeCardImpl accumulativeCard) {
		double result = 0;

		for (Discount discount : discounts) {
			result += discount.getDiscount(order.getPizzas(), accumulativeCard);
		}

		return result;
	}

}
