package org.study.pizzaservice.repository;

import org.study.pizzaservice.domain.Order;

public interface OrderRepository {
    Long saveOrder(Order order);
}
