package org.study.pizzaservice;

import java.util.List;

import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.domain.Pizza;

public interface OrderService {
    Order placeNewOrder(Customer customer, Integer... pizzasID);
    Order createOrder();
}