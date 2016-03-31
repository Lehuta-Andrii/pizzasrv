package org.study.pizzaservice;

import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;

public interface OrderService {
    Order placeNewOrder(Customer customer, Integer... pizzasID);
}