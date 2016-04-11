package org.study.pizzaservice;

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

	AccumulativeCard setCard(Customer customer);

	boolean addSumToCard(Customer customer, double sum);
	
	boolean removeCard(Customer customer);
}
