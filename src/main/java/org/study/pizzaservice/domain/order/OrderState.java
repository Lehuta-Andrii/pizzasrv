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

    public void setContext(OrderContext order);

    public int getPizzasAmount();

    public boolean addPizza(Pizza pizza);

    public boolean removePizza(Pizza pizza);

    public int getId();

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public List<Pizza> getPizzas();

    public void setPizzas(List<Pizza> pizzas);
    
    public boolean canSetState(OrderState state);

}
