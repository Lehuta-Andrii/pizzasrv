package org.study.pizzaservice.simpleservice;

import java.util.List;

import org.study.pizzaservice.PizzasService;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.repository.PizzaRepository;

/**
 * Class represent pizzas storage service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
public class SimplePizzasService implements PizzasService {

	private PizzaRepository pizzaRepository;

	public SimplePizzasService(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}

	@Override
	public List<Pizza> getPizzas() {
		return pizzaRepository.getPizzas();
	}

	@Override
	public boolean setPizzas(List<Pizza> pizzas) {
		return pizzaRepository.setPizzas(pizzas);
	}

	@Override
	public boolean addPizza(Pizza pizza) {
		return pizzaRepository.addPizza(pizza);
	}

	@Override
	public boolean deletePizza(Pizza pizza) {
		return pizzaRepository.deletePizza(pizza);
	}

	@Override
	public Pizza getPizzaById(Integer id) {
		return pizzaRepository.getPizzaByID(id);
	}

}
