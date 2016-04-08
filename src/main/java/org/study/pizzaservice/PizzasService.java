package org.study.pizzaservice;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;

/**
 * Interface for definition of pizza storage service in pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public interface PizzasService {

	List<Pizza> getPizzas();

	boolean setPizzas(List<Pizza> pizzas);

	boolean addPizza(Pizza pizza);

	boolean deletePizza(Pizza pizza);

	Pizza getPizzaById(Integer id);

}
