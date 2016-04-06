package org.study.pizzaservice.repository;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

public interface AccumulativeCardRepository {

    AccumulativeCard getCard(Customer customer);

    boolean addCard(Customer customer);

}
