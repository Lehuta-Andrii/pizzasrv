package org.study.pizzaservice.domain.order;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;

/**
 * Interface for definition of all states of order
 * 
 * @author Andrii Lehuta
 *
 */
public interface OrderState {

    void setContext(OrderContext order);

    int getPizzasAmount();

    boolean addPizza(Pizza pizza);

    boolean removePizza(Pizza pizza);

    int getId();

    Customer getCustomer();

    void setCustomer(Customer customer);

    List<Pizza> getPizzas();

    void setPizzas(List<Pizza> pizzas);
    
    boolean canSetState(OrderState state);

}
