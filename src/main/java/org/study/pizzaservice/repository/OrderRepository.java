package org.study.pizzaservice.repository;

import org.study.pizzaservice.domain.order.Order;

public interface OrderRepository {
    int saveOrder(Order order);
}
