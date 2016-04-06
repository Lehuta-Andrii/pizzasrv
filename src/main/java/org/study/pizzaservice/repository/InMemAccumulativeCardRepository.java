package org.study.pizzaservice.repository;

import java.util.HashMap;
import java.util.Map;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Customer;

public class InMemAccumulativeCardRepository implements AccumulativeCardRepository {

    	private Map<Customer, AccumulativeCard> cards = new HashMap<Customer, AccumulativeCard>(); 
    
	@Override
	public AccumulativeCard getCard(Customer customer) {
		return cards.get(customer);
	}

	@Override
	public boolean addCard(Customer customer) {
	    if(cards.get(customer) == null){
		cards.put(customer, new AccumulativeCardImpl());
		return true;
	    }else{
		return false;
	    }
	}

	
}
