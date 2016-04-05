package org.study.pizzaservice;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

public interface AccumulativeCardService {

	AccumulativeCard getCard(Customer customer);
	AccumulativeCard getCard(Integer id);
	boolean setCard(AccumulativeCard accumulativeCard);
	boolean setCard(AccumulativeCard accumulativeCard, Customer customer);

	
}
