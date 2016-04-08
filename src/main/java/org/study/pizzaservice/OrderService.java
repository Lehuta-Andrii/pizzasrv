package org.study.pizzaservice;

import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.order.OrderState;
import org.study.pizzaservice.domain.customer.Customer;

public interface OrderService {
    Order placeNewOrder(Customer customer, Integer... pizzasID);
    
    public boolean changeOrderStatus(Order order, OrderState state);

    public boolean confirmOrder(Order order);

    public boolean cancelOrder(Order order);

    public boolean accomplishOrder(Order order);

}