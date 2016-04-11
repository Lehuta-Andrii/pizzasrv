package org.study.pizzaservice.repository;

import java.util.List;
import java.util.Optional;

import org.study.pizzaservice.domain.order.Order;

/**
 * Interface that represents repository of orders entity
 * 
 * @author Andrii Lehuta
 *
 */
public interface OrderRepository {

    boolean saveOrder(Order order);

    boolean update(Order order);
    
    Optional<Order> getOrderById(Integer id);
    
    List<Order> getOrders();
    
    boolean removeOrder(Order order);
}
