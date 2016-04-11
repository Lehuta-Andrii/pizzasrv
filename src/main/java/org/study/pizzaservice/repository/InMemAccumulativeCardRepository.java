package org.study.pizzaservice.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Customer;

@Repository
public class InMemAccumulativeCardRepository implements AccumulativeCardRepository {

	private Map<Customer, AccumulativeCard> cards = new HashMap<Customer, AccumulativeCard>();

	public InMemAccumulativeCardRepository(){
		
	}
	
	@Override
	public AccumulativeCard getCard(Customer customer) {
		return cards.get(customer);
	}

	@Override
	public AccumulativeCard addCard(Customer customer) {
		if (cards.containsKey(customer)) {
			return cards.get(customer);

		} else {
			AccumulativeCard card = new AccumulativeCardImpl();
			cards.put(customer, card);
			return card;
		}
	}

}
