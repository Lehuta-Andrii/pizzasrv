package org.study.pizzaservice.simpleservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pizzaservice.AccumulativeCardService;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.AccumulativeCardRepository;

/**
 * Class represent accumulative cards service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
public class SimpleAccumulativeCardService implements AccumulativeCardService {

	private AccumulativeCardRepository cardRepository;

	public SimpleAccumulativeCardService(){
		
	}

	@Autowired
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

	@Override
	public boolean addSumToCard(Customer customer, double sum) {
		boolean result = false;
		AccumulativeCard card = cardRepository.getCard(customer);

		if (card != null) {
			card.addToCard(sum);
			result = true;
		}

		return result;
	}

}
