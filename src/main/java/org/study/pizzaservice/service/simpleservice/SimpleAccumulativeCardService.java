package org.study.pizzaservice.service.simpleservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.accumulativecard.AccumulativeCardImpl;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.AccumulativeCardRepository;
import org.study.pizzaservice.service.AccumulativeCardService;

/**
 * Class represent accumulative cards service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
public class SimpleAccumulativeCardService implements AccumulativeCardService {

    private AccumulativeCardRepository cardRepository;

    public SimpleAccumulativeCardService() {

    }

    @Autowired
    public SimpleAccumulativeCardService(AccumulativeCardRepository cardRepository) {
	this.cardRepository = cardRepository;
    }

    @Override
    public Optional<AccumulativeCard> getCard(Customer customer) {
	return cardRepository.getCard(customer);
    }

    @Override
    public boolean setNewCard(Customer customer) {
	AccumulativeCard card = createEmptyCard();
	card.setCustomer(customer);
	return cardRepository.addCard(card);
    }

    @Override
    public boolean addSumToCard(Customer customer, double sum) {
	boolean result = false;
	Optional<AccumulativeCard> card = cardRepository.getCard(customer);

	if (card.isPresent()) {
	    card.get().addToCard(sum);
	    cardRepository.updateCard(card.get());
	    result = true;
	}

	return result;
    }

    @Override
    public boolean removeCard(Customer customer) {
	return cardRepository.removeCustomerCard(customer);
    }

    @Override
    @Lookup
    public AccumulativeCard createEmptyCard() {
	return new AccumulativeCardImpl();
    }

}
