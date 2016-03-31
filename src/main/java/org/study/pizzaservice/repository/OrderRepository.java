package org.study.pizzaservice.repository;

import org.study.pizzaservice.domain.order.Order;

public interface OrderRepository {
    Long saveOrder(Order order);
}
