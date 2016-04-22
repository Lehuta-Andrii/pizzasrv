package org.study.pizzaservice.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.Pizza;

@Repository
@Transactional
public class HibernatePizzaRepository implements PizzaRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Pizza> getPizzas() {
	Query query = entityManager.createQuery("SELECT p FROM Pizza p");
	return (List<Pizza>) query.getResultList();
    }

    @Override
    public boolean setPizzas(List<Pizza> pizzas) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean addPizza(Pizza pizza) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean deletePizza(Pizza pizza) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean updatePizza(Pizza pizza) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Optional<Pizza> getPizzaByID(Long id) {
	return Optional.of(entityManager.find(Pizza.class, id));
    }

}
