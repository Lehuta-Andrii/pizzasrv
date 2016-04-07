package org.study.pizzaservice.repository;

import java.util.List;

import org.study.pizzaservice.domain.Pizza;

public interface PizzaRepository {
	
	List<Pizza> getPizzas();

	boolean setPizzas(List<Pizza> pizzas);

	boolean addPizza(Pizza pizza);

	boolean deletePizza(Pizza pizza);

    Pizza getPizzaByID(Integer id);
}
