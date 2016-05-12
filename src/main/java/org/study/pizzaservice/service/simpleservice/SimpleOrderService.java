package org.study.pizzaservice.service.simpleservice;

import java.util.*;

import org.study.pizzaservice.service.*;
import org.study.pizzaservice.exceptions.*;
import org.study.pizzaservice.domain.order.*;
import org.study.pizzaservice.domain.customer.*;
import org.study.pizzaservice.domain.order.state.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.repository.OrderRepository;

/**
 * Class represent order service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
@Transactional
public class SimpleOrderService implements OrderService {

	public static final int MAX_NUMBER_OF_PIZZAS = 10;

	private OrderRepository orderRepository;
	private PizzasService pizzasService;

	public SimpleOrderService() {

	}

	@Autowired
	public SimpleOrderService(PizzasService pizzasService, OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
		this.pizzasService = pizzasService;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Order placeNewOrder(Customer customer, Address address, Long... pizzasID) {

		if (pizzasID.length <= MAX_NUMBER_OF_PIZZAS && pizzasID.length > 0) {
			List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
			Order newOrder = createOrder();
			newOrder.setCustomer(customer);
			newOrder.setAddress(address);

			for (Pizza pizza : pizzas) {
				newOrder.addPizza(pizza, 1);
			}

			orderRepository.saveOrder(newOrder);
			return newOrder;
		}

		throw new TooManyPizzasException();
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean confirmOrder(Order order) {
		return changeOrderStatus(order, new InProgressState());
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean cancelOrder(Order order) {
		return changeOrderStatus(order, new CanceledState());
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean accomplishOrder(Order order) {
		return changeOrderStatus(order, new DoneState());
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean changeOrderStatus(Order order, OrderState state) {
		boolean result = order.setState(state);

		if (result) {
			orderRepository.update(order);
		}

		return result;
	}

	@Lookup
	public Order createOrder() {
		return new Order();
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean removeOrder(Order order) {
		return orderRepository.removeOrder(order);
	}

	@Override
	public Order gerOrderById(Long id) {
		Optional<Order> order = orderRepository.getOrderById(id);
		if (order.isPresent()) {
			return order.get();
		}

		throw new OrderWithSpecificIdIsAbsentException();
	}

	private List<Pizza> pizzasByArrOfId(Long... pizzasID) {
		List<Pizza> pizzas = new ArrayList<Pizza>();

		for (Long id : pizzasID) {
			pizzas.add(pizzasService.getPizzaById(id));
		}
		return pizzas;
	}

}
