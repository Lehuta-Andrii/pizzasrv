package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyObject;

import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.Address;
import org.study.pizzaservice.domain.customer.Customer;
import org.study.pizzaservice.exceptions.TooManyPizzasException;
import org.study.pizzaservice.repository.OrderRepository;
import org.study.pizzaservice.service.PizzasService;
import org.study.pizzaservice.service.simpleservice.SimpleOrderService;

@RunWith(MockitoJUnitRunner.class)
public class SimpleOrderServiceTest {

	@Mock
	private Customer mockCustomer;

	@Mock
	private OrderRepository mockOrderRepository;

	@Mock
	private PizzasService mockPizzaService;

	@Mock
	private Pizza mockPizza;
	
	@Mock
	private Address mockAddress;

	private SimpleOrderService orderService;

	@Test(expected = TooManyPizzasException.class)
	public void placeNewOrderTestOnEmptyListOfPizzas() {
		orderService = new SimpleOrderService(mockPizzaService, mockOrderRepository);

		orderService.placeNewOrder(mockCustomer, mockAddress);
	}

	@Test(expected = TooManyPizzasException.class)
	public void placeNewOrderTestOnMoreThanTenPizzas() {
		orderService = new SimpleOrderService(mockPizzaService, mockOrderRepository);

		orderService.placeNewOrder(mockCustomer, mockAddress, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l);
	}

	@Test
	public void placeNewOrderTestOnMoreThanZeroLessThanTenPizzas() {
		orderService = new SimpleOrderService(mockPizzaService, mockOrderRepository);

		when(mockPizzaService.getPizzaById(anyObject())).thenReturn(mockPizza);

		assertTrue(orderService.placeNewOrder(mockCustomer, mockAddress, 1l, 1l, 1l, 1l) != null);
	}

}
