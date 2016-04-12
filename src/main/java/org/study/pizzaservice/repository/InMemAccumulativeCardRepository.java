package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Dummy implementation of Accumulative card repository entity
 * 
 * @author Andrii Lehuta
 *
 */
@Repository
public class InMemAccumulativeCardRepository implements AccumulativeCardRepository {

    private Map<Customer, AccumulativeCard> cards = new HashMap<Customer, AccumulativeCard>();

    public InMemAccumulativeCardRepository() {

    }

    @Override
    public Optional<AccumulativeCard> getCard(Customer customer) {
	if (cards.containsKey(customer)) {
	    return Optional.<AccumulativeCard> of(cards.get(customer));
	}

	return Optional.<AccumulativeCard> empty();
    }

    @Override
    public boolean addCard(Customer customer, AccumulativeCard card) {
	if (cards.containsKey(customer)) {
	    return false;
	} else {
	    cards.put(customer, card);
	    return true;
	}
    }

    @Override
    public List<AccumulativeCard> getCards() {
	return new ArrayList<AccumulativeCard>(cards.values());
    }

    @Override
    public boolean removeCustomerCard(Customer customer) {
	return cards.remove(customer) != null;
    }

}
