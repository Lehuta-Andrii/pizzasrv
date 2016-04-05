package org.study.pizzaservice.simpleservice;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.OrderService;
import org.study.pizzaservice.TooManyPizzas;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.OrderRepository;
import org.study.pizzaservice.repository.PizzaRepository;

public class SimpleOrderService implements OrderService {

	public static int MAX_NUMBER_OF_PIZZAS = 10;

	private PizzaRepository pizzaRepository;
	private OrderRepository orderRepository;

	public SimpleOrderService(PizzaRepository pizzaRepository, OrderRepository orderRepository) {
		this.pizzaRepository = pizzaRepository;
		this.orderRepository = orderRepository;
	}

	public Order placeNewOrder(Customer customer, Integer... pizzasID) {

		if (pizzasID.length <= MAX_NUMBER_OF_PIZZAS && pizzasID.length > 0) {
			List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
			Order newOrder = createOrder(customer, pizzas);

			orderRepository.saveOrder(newOrder);
			return newOrder;
		}

		throw new TooManyPizzas();
	}

	private Order createOrder(Customer customer, List<Pizza> pizzas) {
		Order newOrder = new Order(customer, pizzas);
		return newOrder;
	}

	private List<Pizza> pizzasByArrOfId(Integer... pizzasID) {
		List<Pizza> pizzas = new ArrayList<Pizza>();

		for (Integer id : pizzasID) {
			pizzas.add(pizzaRepository.getPizzaByID(id));
		}
		return pizzas;
	}

}
