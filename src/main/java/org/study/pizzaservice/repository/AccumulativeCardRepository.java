package org.study.pizzaservice.repository;

import java.util.List;
import java.util.Optional;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface that represents repository of accumulative cards entity
 * 
 * @author Andrii Lehuta
 *
 */
public interface AccumulativeCardRepository {

    Optional<AccumulativeCard> getCard(Customer customer);

    boolean addCard(AccumulativeCard card);

    List<AccumulativeCard> getCards();
    
    boolean removeCustomerCard(Customer customer);
    
    boolean updateCard(AccumulativeCard card);
}
