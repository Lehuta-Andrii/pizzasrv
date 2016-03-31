package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.Pizza;

public class InMemPizzaRepository implements PizzaRepository {

    List<Pizza> pizzas = new ArrayList<Pizza>();

    public InMemPizzaRepository() {
	this.pizzas.add(new Pizza("Margarita", 50, Pizza.type.MEAT));
	this.pizzas.add(new Pizza("Americana", 76, Pizza.type.SEA));
	this.pizzas.add(new Pizza("Chilly", 50, Pizza.type.VEGETARIAN));
    }

    @Override
    public Pizza getPizzaByID(Integer id) {
	for (Pizza pizza : pizzas) {
	    if (id.equals(pizza.getId())) {
		return pizza;
	    }
	}

	return null;

    }

}
