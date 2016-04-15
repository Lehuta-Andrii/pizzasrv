package org.study.pizzaservice.repository;

import java.util.List;
import java.util.Optional;

import org.study.pizzaservice.domain.Pizza;

/**
 * Interface that represents repository of pizzas entity
 * 
 * @author Andrii Lehuta
 *
 */
public interface PizzaRepository {

    List<Pizza> getPizzas();

    boolean setPizzas(List<Pizza> pizzas);

    boolean addPizza(Pizza pizza);

    boolean deletePizza(Pizza pizza);

    boolean updatePizza(Pizza pizza);
    
    Optional<Pizza> getPizzaByID(Integer id);
}
