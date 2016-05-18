package org.study.pizzaservice.controllers;

import java.util.*;

import org.apache.commons.collections4.FactoryUtils;
import org.apache.commons.collections4.MapUtils;

import org.study.pizzaservice.domain.Pizza;

public class PizzaAndAmmount {

	private Map<Pizza, String> pizzas = MapUtils.lazyMap(new HashMap<Pizza, String>(),FactoryUtils.instantiateFactory(String.class));

	public PizzaAndAmmount() {
	}

	/**
	 * @return the pizzas
	 */
	public Map<Pizza, String> getPizzas() {
		return pizzas;
	}




	/**
	 * @param pizzas the pizzas to set
	 */
	public void setPizzas(Map<Pizza, String> pizzas) {
		this.pizzas = pizzas;
	}

	
}
