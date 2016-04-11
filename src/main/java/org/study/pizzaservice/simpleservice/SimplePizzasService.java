package org.study.pizzaservice.simpleservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pizzaservice.PizzasService;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.exceptions.PizzaWithSpecificIdIsAbsentException;
import org.study.pizzaservice.repository.PizzaRepository;

/**
 * Class represent pizzas storage service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
public class SimplePizzasService implements PizzasService {

	private PizzaRepository pizzaRepository;

	@Autowired
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
		Optional<Pizza> pizza = pizzaRepository.getPizzaByID(id);
		
		if(pizza.isPresent()){
		    return pizza.get();
		}else{
		    throw new PizzaWithSpecificIdIsAbsentException();
		}
	}

}
