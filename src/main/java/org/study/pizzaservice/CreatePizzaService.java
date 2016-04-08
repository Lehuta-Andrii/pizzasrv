package org.study.pizzaservice;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.Pizza.type;

public class CreatePizzaService {
    public Pizza createPizza(String name, double price, type t){
	return new Pizza(name, price, t);
    }
}
