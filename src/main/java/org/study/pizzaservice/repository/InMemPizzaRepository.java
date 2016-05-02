package org.study.pizzaservice.repository;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.infrastructure.BeanchMark;
import org.study.pizzaservice.infrastructure.PostConstruct;

public class InMemPizzaRepository implements PizzaRepository {

    List<Pizza> pizzas = new ArrayList<Pizza>();

    public InMemPizzaRepository() {
	this.pizzas.add(new Pizza("Margarita", 50, Pizza.type.MEAT));
	this.pizzas.add(new Pizza("Americana", 76, Pizza.type.SEA));
	this.pizzas.add(new Pizza("Chilly", 50, Pizza.type.VEGETARIAN));
	
    }
    @PostConstruct
    public void cookPizzas() {
    }
    
    

    /**
     * @return the pizzas
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }
    /**
     * @param pizzas the pizzas to set
     */
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
    public void init(){
	System.out.println("init called by ioc");
    }
    
    @Override
    @BeanchMark
    public Pizza getPizzaByID(Integer id) {
	for (Pizza pizza : pizzas) {
	    if (id.equals(pizza.getId())) {
		return pizza;
	    }
	}

	return null;

    }
    @Override
    public Pizza create(Pizza pizza) {
	// TODO Auto-generated method stub
	return null;
    }

}
