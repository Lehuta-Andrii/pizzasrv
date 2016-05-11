package org.study.pizzaservice.service.simpleservice;

import java.util.*;

import org.springframework.stereotype.Service;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.discount.Discount;
import org.study.pizzaservice.domain.discount.DiscountImpl;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.service.DiscountService;

/**
 * Class represent discount service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
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
	public double countDiscount(Order order, Optional<AccumulativeCard> accumulativeCard) {
		double result = 0;

		for (Discount discount : discounts) {
			result += discount.getDiscount(order.getPizzasMap(), accumulativeCard);
		}

		return result;
	}

	@Override
	public double countDiscount(Order order, Optional<AccumulativeCard> accumulativeCard, List<Discount> discounts) {
		double result = 0;

		for (Discount discount : this.discounts) {
			if (discounts.contains(discount)) {
				result += discount.getDiscount(order.getPizzasMap(), accumulativeCard);
			}
		}

		return result;
	}

	@Override
	public boolean removeDiscount(Discount discount) {
	    return discounts.remove(discount);
	}

}
