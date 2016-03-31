package org.study.pizzaservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.study.pizzaservice.domain.AccumulativeCard;
import org.study.pizzaservice.domain.Customer;
import org.study.pizzaservice.domain.Discount;
import org.study.pizzaservice.domain.DiscountImpl;
import org.study.pizzaservice.domain.Order;
import org.study.pizzaservice.domain.Pizza;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DiscountImplTest {

	@Mock
	private Customer mockCustomer;
	
	@Mock
	private AccumulativeCard accumulativeCard;

	@Mock
	List<Pizza> pizzas;

	private Discount discount;

	@Before
	public void setUp() {
		discount = new DiscountImpl();
	}

	@Test
	public void getDiscountWithPizzasLessThenFourWithoutAccumCardTest() {
		Order order = new Order(mockCustomer, Collections.emptyList());
		Order spyOrder = spy(order);

		double pizzasPrice = 135;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		when(mockCustomer.hasAccumulativeCard()).thenReturn(false);
		when(spyOrder.getPizzas()).thenReturn(pizzasList);
		when(spyOrder.getPrice()).thenReturn(pizzasPrice);

		assertTrue(Double.compare(discount.getDiscount(spyOrder), 0) == 0);
	}

	@Test
	public void getDiscountWithFourPizzasWithoutAccumCardTest() {
		Order order = new Order(mockCustomer, Collections.emptyList());
		Order spyOrder = spy(order);

		double pizzasPrice = 235;
		double priceOfMostExpensivePizza = 100;
		double expectedDiscount = priceOfMostExpensivePizza * 0.3;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza4", priceOfMostExpensivePizza, Pizza.type.VEGETARIAN));

		when(mockCustomer.hasAccumulativeCard()).thenReturn(false);
		when(spyOrder.getPizzas()).thenReturn(pizzasList);
		when(spyOrder.getPrice()).thenReturn(pizzasPrice);
		when(spyOrder.getPizzasAmount()).thenReturn(pizzasList.size());

		assertTrue(Double.compare(discount.getDiscount(spyOrder), expectedDiscount) == 0);
	}

	@Test
	public void getDiscountWithPizzasLessThanFourWithSmallAccumCardSumTest() {
		Order order = new Order(mockCustomer, Collections.emptyList());
		Order spyOrder = spy(order);

		double pizzasPrice = 135;
		double sumOnAccumulativeCard = 100;
		double expectedDiscount = sumOnAccumulativeCard * 0.1;

		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
		when(mockCustomer.getAccumulativeCard()).thenReturn(accumulativeCard);
		when(accumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);
		when(spyOrder.getPizzas()).thenReturn(pizzasList);
		when(spyOrder.getPrice()).thenReturn(pizzasPrice);
		when(spyOrder.getPizzasAmount()).thenReturn(pizzasList.size());

		assertTrue(Double.compare(discount.getDiscount(spyOrder), expectedDiscount) == 0);
	}

	@Test
	public void getDiscountWithPizzasLessThanFourWithLargeAccumCardSumTest() {
		Order order = new Order(mockCustomer, Collections.emptyList());
		Order spyOrder = spy(order);

		double pizzasPrice = 135;
		double sumOnAccumulativeCard = 100000;
		double expectedDiscount = pizzasPrice * 0.3;

		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));

		when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
		when(mockCustomer.getAccumulativeCard()).thenReturn(accumulativeCard);
		when(accumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);
		when(spyOrder.getPizzas()).thenReturn(pizzasList);
		when(spyOrder.getPrice()).thenReturn(pizzasPrice);
		when(spyOrder.getPizzasAmount()).thenReturn(pizzasList.size());

		assertTrue(Double.compare(discount.getDiscount(spyOrder), expectedDiscount) == 0);
	}
	
	@Test
	public void getDiscountWithPizzasMoreThanFourWithSmallAccumCardSumTest() {
		Order order = new Order(mockCustomer, Collections.emptyList());
		Order spyOrder = spy(order);

		double pizzasPrice = 235;
		double sumOnAccumulativeCard = 100;
		double priceOfMostExpensivePizza = 100;
		double expectedDiscount = (0.3 * priceOfMostExpensivePizza) + sumOnAccumulativeCard * 0.1;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza4", priceOfMostExpensivePizza, Pizza.type.VEGETARIAN));

		when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
		when(mockCustomer.getAccumulativeCard()).thenReturn(accumulativeCard);
		when(accumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);
		when(spyOrder.getPizzas()).thenReturn(pizzasList);
		when(spyOrder.getPrice()).thenReturn(pizzasPrice);
		when(spyOrder.getPizzasAmount()).thenReturn(pizzasList.size());

		assertTrue(Double.compare(discount.getDiscount(spyOrder), expectedDiscount) == 0);
	}

	@Test
	public void getDiscountWithPizzasMoreThanFourWithLargeAccumCardSumTest() {
		Order order = new Order(mockCustomer, Collections.emptyList());
		Order spyOrder = spy(order);

		double pizzasPrice = 235;
		double sumOnAccumulativeCard = 1000000;
		double priceOfMostExpensivePizza = 100;
		double expectedDiscount = (0.3 * priceOfMostExpensivePizza)
				+ (pizzasPrice - (0.3 * priceOfMostExpensivePizza)) * 0.30;
		List<Pizza> pizzasList = new ArrayList<Pizza>();

		pizzasList.add(new Pizza("Pizza1", 45, Pizza.type.MEAT));
		pizzasList.add(new Pizza("Pizza2", 45, Pizza.type.SEA));
		pizzasList.add(new Pizza("Pizza3", 45, Pizza.type.VEGETARIAN));
		pizzasList.add(new Pizza("Pizza4", priceOfMostExpensivePizza, Pizza.type.VEGETARIAN));

		when(mockCustomer.hasAccumulativeCard()).thenReturn(true);
		when(mockCustomer.getAccumulativeCard()).thenReturn(accumulativeCard);
		when(accumulativeCard.getSum()).thenReturn(sumOnAccumulativeCard);
		when(spyOrder.getPizzas()).thenReturn(pizzasList);
		when(spyOrder.getPrice()).thenReturn(pizzasPrice);
		when(spyOrder.getPizzasAmount()).thenReturn(pizzasList.size());

		assertTrue(Double.compare(discount.getDiscount(spyOrder), expectedDiscount) == 0);
	}

}
