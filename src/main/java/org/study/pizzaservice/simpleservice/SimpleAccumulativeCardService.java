package org.study.pizzaservice.simpleservice;

import org.study.pizzaservice.AccumulativeCardService;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.AccumulativeCardRepository;

public class SimpleAccumulativeCardService implements AccumulativeCardService {

	private AccumulativeCardRepository cardRepository;

	public SimpleAccumulativeCardService(AccumulativeCardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Override
	public AccumulativeCard getCard(Customer customer) {
		return cardRepository.getCard(customer);
	}

	@Override
	public AccumulativeCard setCard(Customer customer) {
		return cardRepository.addCard(customer);
	}

}
