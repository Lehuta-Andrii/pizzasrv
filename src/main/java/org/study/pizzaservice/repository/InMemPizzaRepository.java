package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.infrastructure.BeanchMark;
import org.study.pizzaservice.infrastructure.PostConstruct;

public class InMemPizzaRepository implements PizzaRepository {

    List<Pizza> pizzas = new ArrayList<Pizza>();

    public InMemPizzaRepository() {
    }
    @PostConstruct
    public void cookPizzas() {
	this.pizzas.add(new Pizza("Margarita", 50, Pizza.type.MEAT));
	this.pizzas.add(new Pizza("Americana", 76, Pizza.type.SEA));
	this.pizzas.add(new Pizza("Chilly", 50, Pizza.type.VEGETARIAN));
    }

    public void init(){
	System.out.println("init called by ioc");
    }
    
    @BeanchMark(active = true)
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
