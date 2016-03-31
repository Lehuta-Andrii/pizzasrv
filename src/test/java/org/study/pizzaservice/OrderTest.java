package org.study.pizzaservice;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.study.pizzaservice.domain.Discount;
import org.study.pizzaservice.domain.order.CanceledOrder;
import org.study.pizzaservice.domain.order.DoneOrder;
import org.study.pizzaservice.domain.order.InProgressOrder;
import org.study.pizzaservice.domain.order.Order;
import org.study.pizzaservice.domain.Pizza;
import org.study.pizzaservice.domain.customer.AccumulativeCard;
import org.study.pizzaservice.domain.customer.Customer;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    
    	@Mock
	private Customer mockCustomer;
	
	@Mock
	private Discount discount;

	@Mock
	private Discount mockDiscount;
	
	@Mock
	private AccumulativeCard mockAccumulativeCard;

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

		order = new Order(mockCustomer, Collections.<Pizza>emptyList());
		assertTrue(Double.compare(order.getDiscount(), 0) == 0);

	}

	@Test
	public void getDicountTestOnDiscountObject() {
		
		double expectedDiscount = 40;

		order = new Order(mockCustomer, Collections.<Pizza>emptyList());
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
		
		order = new Order(mockCustomer, Collections.<Pizza>emptyList());
				
		assertTrue(order.addPizza(new Pizza("Pizza1", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == 1);

	}
	
	
	@Test
	public void addPizzaTestOnPizzaListWithInProgressOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount();
		
		order.setState(new InProgressOrder());
	
		assertFalse(order.addPizza(new Pizza("Pizza11", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

	}
	
	@Test
	public void addPizzaTestOnPizzaListInCancelledOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount();
		
		order.setState(new CanceledOrder());
	
		assertFalse(order.addPizza(new Pizza("Pizza11", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

	}
	
	@Test
	public void addPizzaTestOnPizzaListInDoneOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		order = new Order(mockCustomer, pizzasList);
		int expectedPizzasAmount = order.getPizzasAmount();
		
		order.setState(new DoneOrder());
		
		assertFalse(order.addPizza(new Pizza("Pizza11", 45, Pizza.type.MEAT)));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

	}
	
	@Test
	public void removePizzaTestOnEmptyPizzaListAndNewOrder() {
		
		order = new Order(mockCustomer, Collections.<Pizza>emptyList());
				
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
	public void removePizzaTestOnListOfPizzasAndInProgressOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		Pizza specific = new Pizza("Specific Pizza", 45, Pizza.type.VEGETARIAN);
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(specific);
		
		
		order = new Order(mockCustomer, pizzasList);
		order.setState(new InProgressOrder());
		int expectedPizzasAmount = order.getPizzasAmount();
	
		assertFalse(order.removePizza(specific));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);

	}
	
	@Test
	public void removePizzaTestOnListOfPizzasInCanceledOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		Pizza specific = new Pizza("Specific Pizza", 45, Pizza.type.VEGETARIAN);
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(specific);
		
		
		order = new Order(mockCustomer, pizzasList);
		order.setState(new CanceledOrder());
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
		order.setState(new DoneOrder());
		int expectedPizzasAmount = order.getPizzasAmount();
	
		assertFalse(order.removePizza(specific));
		assertTrue(order.getPizzasAmount() == expectedPizzasAmount);
	
	}

	@Test
	public void updateCustomerCardTestOnOrderDone() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		
		double expectedPrice = 135;
		when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
		when(mockCustomer.getAccumulativeCard()).thenReturn(mockAccumulativeCard);
		
		order = new Order(mockCustomer, pizzasList);
		order.setState(new DoneOrder());
		
		assertTrue(order.updateCustomerCard());
		
		verify(mockAccumulativeCard).addToCard(expectedPrice);
	}
	
	@Test
	public void updateCustomerCardTestOnNewOrder() {
		
		List<Pizza> pizzasList = new ArrayList<Pizza>();
		
		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
				
		order = new Order(mockCustomer, pizzasList);
		
		assertFalse(order.updateCustomerCard());
		
	}
	

	 
   }
