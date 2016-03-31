package org.study.pizzaservice;

import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.customer.Customer;

public interface OrderService {
    Order placeNewOrder(Customer customer, Integer... pizzasID);
}