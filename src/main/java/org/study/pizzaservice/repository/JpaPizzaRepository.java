package org.study.pizzaservice.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.Pizza;

@Transactional
@Repository("pizzaRepository")
public class JpaPizzaRepository implements PizzaRepository{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Pizza getPizzaByID(Integer id) {
	return entityManager.find(Pizza.class, id);

    }

    @Override
    public Pizza create(Pizza pizza) {
	entityManager.persist(pizza);
	return pizza;
    }

}
