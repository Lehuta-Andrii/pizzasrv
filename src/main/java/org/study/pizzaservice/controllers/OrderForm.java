package org.study.pizzaservice.controllers;

import java.util.*;

import org.apache.commons.collections4.FactoryUtils;
import org.apache.commons.collections4.MapUtils;

public class OrderForm {

	private Map<Long, String> pizzas = MapUtils.lazyMap(new HashMap<Long, String>(),FactoryUtils.instantiateFactory(String.class));

	public OrderForm() {
	}

	/**
	 * @return the pizzas
	 */
	public Map<Long, String> getPizzas() {
		return pizzas;
	}

	/**
	 * @param pizzas the pizzas to set
	 */
	public void setPizzas(Map<Long, String> pizzas) {
		this.pizzas = pizzas;
	}

	


}
