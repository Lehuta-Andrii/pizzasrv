package org.study.pizzaservice.simpleservice;

import java.util.ArrayList;
import java.util.List;

import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.order.OrderState;
import org.study.pizzaservice.domain.order.state.CanceledState;
import org.study.pizzaservice.domain.order.state.DoneState;
import org.study.pizzaservice.domain.order.state.InProgressState;
import org.study.pizzaservice.exceptions.TooManyPizzasException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.study.pizzaservice.OrderService;
import org.study.pizzaservice.PizzasService;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.repository.OrderRepository;

/**
 * Class represent order service entity o of pizza service
 * 
 * @author Andrii_Lehuta
 *
 */
@Service
public class SimpleOrderService implements OrderService {

	public static final int MAX_NUMBER_OF_PIZZAS = 10;

	private OrderRepository orderRepository;
	private PizzasService pizzasService;

	public SimpleOrderService(){
		
	}
	
	@Autowired
	public SimpleOrderService(PizzasService pizzasService, OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
		this.pizzasService = pizzasService;
	}

	public Order placeNewOrder(Customer customer, Integer... pizzasID) {

		if (pizzasID.length <= MAX_NUMBER_OF_PIZZAS && pizzasID.length > 0) {
			List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
			Order newOrder = createOrder();//(customer, pizzas);
			newOrder.setCustomer(customer);
			newOrder.setPizzas(pizzas);
			
			orderRepository.saveOrder(newOrder);
			return newOrder;
		}

		throw new TooManyPizzasException();
	}

	@Override
	public boolean confirmOrder(Order order) {
		return changeOrderStatus(order, new InProgressState());
	}

	@Override
	public boolean cancelOrder(Order order) {
		return changeOrderStatus(order, new CanceledState());
	}

	@Override
	public boolean accomplishOrder(Order order) {
		return changeOrderStatus(order, new DoneState());
	}

	@Override
	public boolean changeOrderStatus(Order order, OrderState state) {
		boolean result = order.setState(state);

		if (result) {
			orderRepository.update(order);
		}

		return result;
	}

	@Lookup
	public Order createOrder() {//Customer customer, List<Pizza> pizzas) {
		//Order newOrder = new Order(customer, pizzas);
		//return newOrder;
		return null;
	}

	private List<Pizza> pizzasByArrOfId(Integer... pizzasID) {
		List<Pizza> pizzas = new ArrayList<Pizza>();

		for (Integer id : pizzasID) {
			pizzas.add(pizzasService.getPizzaById(id));
		}
		return pizzas;
	}

}
