package org.study.pizzaservice;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

public interface AccumulativeCardService {

    AccumulativeCard getCard(Customer customer);

    boolean setCard(Customer customer);

}
