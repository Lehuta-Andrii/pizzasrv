package org.study.pizzaservice.repository;

import org.study.pizzaservice.domain.Pizza;

public interface PizzaRepository {
    Pizza getPizzaByID(Integer id);
    Pizza create(Pizza pizza);
}
