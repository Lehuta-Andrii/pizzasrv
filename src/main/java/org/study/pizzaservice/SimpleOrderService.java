package org.study.pizzaservice;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.repository.InMemOrderRepository;
import org.study.pizzaservice.repository.InMemPizzaRepository;
import org.study.pizzaservice.repository.OrderRepository;
import org.study.pizzaservice.repository.PizzaRepository;

public class SimpleOrderService implements OrderService {

	private PizzaRepository pizzaRepository = new InMemPizzaRepository();
	private OrderRepository orderRepository = new InMemOrderRepository();

	public SimpleOrderService() {
	}

	public Order placeNewOrder(Customer customer, Integer... pizzasID) {
		List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
		Order newOrder = createOrder(customer, pizzas);

		orderRepository.saveOrder(newOrder); // set Order Id and save Order to
		// in-memory list
		return newOrder;
	}

	private Order createOrder(Customer customer, List<Pizza> pizzas) {
		Order newOrder = new Order(customer, pizzas);
		return newOrder;
	}

	private List<Pizza> pizzasByArrOfId(Integer... pizzasID) {
		List<Pizza> pizzas = new ArrayList<Pizza>();

		for (Integer id : pizzasID) {
			pizzas.add(pizzaRepository.getPizzaByID(id)); // get Pizza from
			// predifined
			// in-memory
			// list
		}
		return pizzas;
	}

}
