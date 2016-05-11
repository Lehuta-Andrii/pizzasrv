package org.study.pizzaservice.repository.jparepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.repository.PizzaRepository;

@Repository
public class JpaPizzaRepository implements PizzaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Pizza> getPizzas() {
		TypedQuery<Pizza> query = entityManager.createNamedQuery("Pizza.getPizzas", Pizza.class);
		return query.getResultList();
	}

	@Override
	public boolean setPizzas(List<Pizza> pizzas) {
		for (Pizza pizza : pizzas) {
			try {
				entityManager.persist(pizza);
			} catch (PersistenceException ex) {
				System.err.println(ex);
				return false;
			}
		}
		entityManager.flush();
		return true;
	}

	@Override
	public boolean addPizza(Pizza pizza) {
		try {
			entityManager.persist(pizza);
			entityManager.flush();
		} catch (PersistenceException ex) {
			System.err.println(ex);
			return false;
		}

		return true;
	}

	@Override
	public boolean deletePizza(Pizza pizza) {
		Pizza dbPizza = entityManager.find(Pizza.class, pizza.getId());
		if (dbPizza != null) {
			entityManager.remove(dbPizza);
			entityManager.flush();
			return true;
		}

		return false;
	}

	@Override
	public boolean updatePizza(Pizza pizza) {
		Pizza dbPizza = entityManager.find(Pizza.class, pizza.getId());
		if (dbPizza != null) {
			entityManager.merge(pizza);
			return true;
		}
		return false;
	}

	@Override
	public Optional<Pizza> getPizzaByID(Long id) {
		return Optional.of(entityManager.find(Pizza.class, id));
	}

}
