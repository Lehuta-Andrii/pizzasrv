package org.study.pizzaservice.service;

import java.util.Optional;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface for definition of Accumulative card service in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public interface AccumulativeCardService {

	Optional<AccumulativeCard> getCard(Customer customer);

	boolean setNewCard(Customer customer);
	
	boolean setCard(Customer customer, AccumulativeCard card);

	boolean addSumToCard(Customer customer, double sum);
	
	boolean removeCard(Customer customer);
	
	AccumulativeCard createEmptyCard();
}
