package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyObject;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.exceptions.TooManyPizzas;
import org.study.pizzaservice.repository.OrderRepository;
import org.study.pizzaservice.repository.PizzaRepository;
import org.study.pizzaservice.simpleservice.SimpleOrderService;

@RunWith(MockitoJUnitRunner.class)
public class SimpleOrderServiceTest {

	@Mock
	private Customer mockCustomer;

	@Mock
	private OrderRepository mockOrderRepository;

	@Mock
	private PizzaRepository mockPizzaRepository;

	@Mock
	private Pizza mockPizza;

	private SimpleOrderService orderService;

	@Test(expected = TooManyPizzas.class)
	public void placeNewOrderTestOnEmptyListOfPizzas() {
		orderService = new SimpleOrderService(mockPizzaRepository, mockOrderRepository);

		orderService.placeNewOrder(mockCustomer);
	}

	@Test(expected = TooManyPizzas.class)
	public void placeNewOrderTestOnMoreThanTenPizzas() {
		orderService = new SimpleOrderService(mockPizzaRepository, mockOrderRepository);

		orderService.placeNewOrder(mockCustomer, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	}

	@Test
	public void placeNewOrderTestOnMoreThanZeroLessThanTenPizzas() {
		orderService = new SimpleOrderService(mockPizzaRepository, mockOrderRepository);

		when(mockPizzaRepository.getPizzaByID(anyObject())).thenReturn(mockPizza);

		assertTrue(orderService.placeNewOrder(mockCustomer, 1, 1, 1, 1) != null);
	}

}
