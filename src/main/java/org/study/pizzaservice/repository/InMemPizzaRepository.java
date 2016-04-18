package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.Pizza;

/**
 * Dummy implementation of Pizza repository entity
 * 
 * @author Andrii Lehuta
 *
 */
// @Repository
public class InMemPizzaRepository implements PizzaRepository {

    List<Pizza> pizzas = new ArrayList<Pizza>();

    public InMemPizzaRepository() {
	this.pizzas.add(new Pizza(1, "Margarita", 50, Pizza.Type.MEAT));
	this.pizzas.add(new Pizza(2, "Americana", 76, Pizza.Type.SEA));
	this.pizzas.add(new Pizza(3, "Chilly", 50, Pizza.Type.VEGETARIAN));
    }

    @Override
    public Optional<Pizza> getPizzaByID(Integer id) {
	for (Pizza pizza : pizzas) {
	    if (id.equals(pizza.getId())) {
		return Optional.<Pizza> of(pizza);
	    }
	}

	return Optional.empty();

    }

    @Override
    public List<Pizza> getPizzas() {
	return pizzas;
    }

    @Override
    public boolean setPizzas(List<Pizza> pizzas) {
	if (pizzas != null) {
	    this.pizzas = pizzas;
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean addPizza(Pizza pizza) {
	if (pizzas.contains(pizza)) {
	    return false;
	} else {
	    pizzas.add(pizza);
	    return true;
	}
    }

    @Override
    public boolean deletePizza(Pizza pizza) {
	if (pizzas.contains(pizza)) {
	    return pizzas.remove(pizza);
	} else {
	    return false;
	}
    }

    @Override
    public boolean updatePizza(Pizza pizza) {
	return true;
    }

}
