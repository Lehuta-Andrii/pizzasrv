package org.study.pizzaservice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Discount;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.domain.Pizza;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

	@Mock
	private Customer mockCustomer;
	
	@Mock
	private Discount discount;

	@Mock
	private Discount mockDiscount;

	private Order order;

	@Test
	public void getPriceTestOnEmptyPizzaList() {
		when(mockCustomer.hasAccumulativeCard()).thenReturn(false);

		List<Pizza> emptyPizzasList = new ArrayList<Pizza>();
		order = new Order(mockCustomer, emptyPizzasList);

		assertTrue(Double.compare(0, order.getPrice()) == 0);
	}

	@Test
	public void getPriceTestOnPizzaList() {

		double expectedPrice = 135;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		order = new Order(mockCustomer, pizzasList);
		assertTrue(Double.compare(order.getPrice(), expectedPrice) == 0);
	}

	@Test
	public void getDicountTestOnNullDiscountObject() {

		order = new Order(mockCustomer, Collections.EMPTY_LIST);
		assertTrue(Double.compare(order.getDiscount(), 0) == 0);

	}

	@Test
	public void getDicountTestOnDiscountObject() {
		
		double expectedDiscount = 40;

		order = new Order(mockCustomer, Collections.EMPTY_LIST);
		order.setDiscount(mockDiscount);
		
		when(mockDiscount.getDiscount(order)).thenReturn(expectedDiscount);
		
		assertTrue(Double.compare(order.getDiscount(), expectedDiscount) == 0);

	}
	
	@Test
	public void getPriceWithDiscountTestWithoutDiscount() {
			
		double expectedPrice = 135;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		
		order = new Order(mockCustomer, pizzasList);
		
		assertTrue(Double.compare(order.getPriceWithDiscount(), expectedPrice) == 0);

	}
	
	@Test
	public void getPriceWithDiscountTestWithDiscount() {
		
		double expectedDiscount = 40;
		double expectedPrice = 135;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		

		order = new Order(mockCustomer, pizzasList);
		order.setDiscount(mockDiscount);
		
		when(mockDiscount.getDiscount(order)).thenReturn(expectedDiscount);
		
		assertTrue(Double.compare(order.getPriceWithDiscount(), expectedPrice + expectedDiscount) == 0);

	}
	
	@Test
	public void addPizzaTestOnEmptyPizzaListAndNewOrder() {
		
		order = new Order(mockCustomer, Collections.EMPTY_LIST);
				
		assertTrue(order.addPizza(new Pizza("Pizza1", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == 1);

	}
	
	@Test
	public void addPizzaTestOnPizzaListWithTenPizzasAndNewOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza4", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza5", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza6", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza7", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza8", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza9", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza10", 45, Pizza.type.VEGETARIAN));
		
		
		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount();
	
		assertFalse(order.addPizza(new Pizza("Pizza11", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount );

	}
	
	@Test
	public void addPizzaTestOnPizzaListWithLessThenTenPizzasAndInProgressOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount();
		
		order.confirmOrder();
	
		assertFalse(order.addPizza(new Pizza("Pizza11", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

	}
	
	@Test
	public void addPizzaTestOnPizzaListWithLessThenTenPizzasAndInCancelledOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount();
		
		order.cancelOrder();
	
		assertFalse(order.addPizza(new Pizza("Pizza11", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);
	}
	
	@Test
	public void addPizzaTestOnPizzaListWithLessThenTenPizzasAndInDoneOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount();
		
		order.confirmOrder();
		order.completeOrder();
		
		assertFalse(order.addPizza(new Pizza("Pizza11", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

	}
	
	@Test
	public void removePizzaTestOnEmptyPizzaListAndNewOrder() {
		
		order = new Order(mockCustomer, Collections.EMPTY_LIST);
				
		assertFalse(order.removePizza(new Pizza("Pizza1", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == 0);

	}
	
	@Test
	public void removePizzaTestOnPizzaListAndNewOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		Pizza specific = new Pizza("Specific Pizza", 45, Pizza.type.VEGETARIAN);
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(specific);
		
		
		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount() - 1;
	
		assertTrue(order.removePizza(specific));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);
	}
	
	@Test
	public void removePizzaTestOnListLessThanTenPizzasAndInProgressOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		Pizza specific = new Pizza("Specific Pizza", 45, Pizza.type.VEGETARIAN);
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(specific);
		
		
		order = new Order(mockCustomer, pizzasList);
		order.confirmOrder();
		int expectedPizzasAmount = order.getPizzasAmount();
	
		assertFalse(order.removePizza(specific));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);
	
	}
	
	@Test
	public void removePizzaTestOnListLessThanTenPizzasAndInCanceledOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		Pizza specific = new Pizza("Specific Pizza", 45, Pizza.type.VEGETARIAN);
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(specific);
		
		
		order = new Order(mockCustomer, pizzasList);
		order.cancelOrder();
		int expectedPizzasAmount = order.getPizzasAmount();
	
		assertFalse(order.removePizza(specific));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);
	
	}
	
	@Test
	public void removePizzaTestOnListLessThanTenPizzasAndInDoneOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		Pizza specific = new Pizza("Specific Pizza", 45, Pizza.type.VEGETARIAN);
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(specific);
		
		
		order = new Order(mockCustomer, pizzasList);
		order.confirmOrder();
		order.completeOrder();
		int expectedPizzasAmount = order.getPizzasAmount();
	
		assertFalse(order.removePizza(specific));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);
	
	}

	
}
