package org.study.pizzaservice;

import org.study.pizzaservice.domain.accumulativecard.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

public interface AccumulativeCardService {

    AccumulativeCard getCard(Customer customer);

    AccumulativeCard setCard(Customer customer);

    boolean addSumToCard(Customer customer, double sum);
}
