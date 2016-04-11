package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.Pizza;

@Repository
public class InMemPizzaRepository implements PizzaRepository {

	List<Pizza> pizzas = new ArrayList<Pizza>();

	public InMemPizzaRepository() {
		this.pizzas.add(new Pizza("Margarita", 50, Pizza.Type.MEAT));
		this.pizzas.add(new Pizza("Americana", 76, Pizza.Type.SEA));
		this.pizzas.add(new Pizza("Chilly", 50, Pizza.Type.VEGETARIAN));
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

	@Override
	public List<Pizza> getPizzas() {
		return Collections.unmodifiableList(pizzas);
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

}
